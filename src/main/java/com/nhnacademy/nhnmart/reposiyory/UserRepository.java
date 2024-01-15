package com.nhnacademy.nhnmart.reposiyory;

import com.nhnacademy.nhnmart.domain.Role;
import com.nhnacademy.nhnmart.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    boolean exists(String id);
    boolean matches(String id, String password);
    User getUser(String id);
    User addUser(String id, String password, Role role, String name);
}
