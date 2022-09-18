package cc.hqido.spms.core.service.impl;

import cc.hqido.spms.core.ResultBuilderConsts;
import cc.hqido.spms.core.model.User;
import cc.hqido.spms.core.model.enums.StatusEnum;
import cc.hqido.spms.core.service.UserService;
import cc.hqido.spms.core.util.PasswordUtils;
import cc.hqido.spms.infra.entity.UserEntity;
import cc.hqido.spms.infra.repository.UserRepository;
import cc.hqido.spms.infra.util.Asserts;
import cc.hqido.spms.infra.util.BeanUtils;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    private final static Cache<Long, User> ID_CACHE = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Override
    public User get(String username) {
        return convert(userRepository.getByUsername(username));
    }

    @Override
    public User get(Long id) {
        return ID_CACHE.get(id, key -> convert(userRepository.getReferenceById(id)));
    }

    @Override
    public void save(User user) {
        // check repeat username
        UserEntity query = new UserEntity();
        query.setUsername(user.getUsername());
        Asserts.isFalse(userRepository.exists(Example.of(query)), ResultBuilderConsts.USER_ALREADY_EXISTS);

        // convert entity
        UserEntity entity = convert(user);

        // hash password and default value
        entity.setPassword(PasswordUtils.genPassword(user.getPassword()));
        entity.setStatus(StatusEnum.ENABLE);

        userRepository.save(entity);
    }

    @Override
    public void update(User user) {
        Asserts.isFalse(user.isNew(), ResultBuilderConsts.NULL, "id");

        UserEntity entity = userRepository.getReferenceById(user.getId());
        boolean modified = false;
        if (StringUtils.isNotBlank(user.getNickName())) {
            entity.setNickName(user.getNickName());
            modified = true;
        }

        if (StringUtils.isNotBlank(user.getPassword())) {
            entity.setPassword(PasswordUtils.genPassword(user.getPassword()));
            modified = true;
        }

        if (modified) {
            userRepository.save(entity);
        }

    }

    private User convert(UserEntity entity) {
        return BeanUtils.copy(entity, new User());
    }

    private UserEntity convert(User user) {
        return BeanUtils.copy(user, new User());
    }

}
