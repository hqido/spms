package cc.hqido.spms.core.service;

import cc.hqido.spms.core.model.User;

public interface UserService {
    User get(String username);

    User get(Long id);

    void save(User user);

    void update(User user);
}
