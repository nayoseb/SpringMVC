package com.nhnacademy.nhnmart.reposiyory;

import com.nhnacademy.nhnmart.domain.Role;
import com.nhnacademy.nhnmart.domain.User;
import com.nhnacademy.nhnmart.exception.UserAlreadyExistsException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public boolean exists(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getUser(id))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public User getUser(String id) {
        return exists(id) ? userMap.get(id) : null;
    }

    @Override
    public User addUser(String id, String password, Role role, String name) {
        if (exists(id)) {
            log.info("사용자 추가 시도 실패: '{}' 아이디를 가진 사용자가 이미 존재합니다.", id);
            throw new UserAlreadyExistsException("'" + id + "' 아이디를 가진 사용자가 이미 존재합니다.");
        }

        User user = User.builder()
                .id(id)
                .password(password)
                .role(role)
                .name(name)
                .build();

        userMap.put(id, user);

        return user;
    }
}
