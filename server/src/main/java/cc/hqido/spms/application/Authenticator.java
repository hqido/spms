package cc.hqido.spms.application;

import cc.hqido.spms.adapter.security.SecurityConfig;
import cc.hqido.spms.adapter.security.Subject;
import cc.hqido.spms.core.ResultBuilderConsts;
import cc.hqido.spms.core.model.User;
import cc.hqido.spms.core.service.UserService;
import cc.hqido.spms.core.util.PasswordUtils;
import cc.hqido.spms.infra.util.Asserts;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class Authenticator {

    @Resource
    private UserService userService;

    @Resource
    private SecurityConfig securityConfig;

    private Algorithm algorithm;

    public User auth(String username, String password) {
        User user = userService.get(username);
        Asserts.nonNull(user, ResultBuilderConsts.AUTHENTICATE_FAIL);

        Asserts.isTrue(PasswordUtils.verify(password, user.getPassword()), ResultBuilderConsts.AUTHENTICATE_FAIL);
        return user;
    }

    public String getIssuer() {
        return (securityConfig.isSecure() ? "https://" : "http://") + securityConfig.getDomain();
    }

    public String getSubject() {
        return securityConfig.getDomain();
    }

    public SecurityConfig securityConfig() {
        return securityConfig;
    }

    public Algorithm getAlgorithm() {
        if (algorithm == null) {
            synchronized (this) {
                if (algorithm == null) {
                    algorithm = Algorithm.HMAC256(securityConfig.getSecret() + UUID.randomUUID());
                }
            }
        }

        return algorithm;
    }

    public void verify(Subject subject) {
        User user = userService.get(subject.getUserId());

        if (user == null || user.getStatus())
    }
}
