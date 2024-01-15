package com.nhnacademy.nhnmart.reposiyory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nhnacademy.nhnmart.domain.Role;
import com.nhnacademy.nhnmart.domain.User;
import com.nhnacademy.nhnmart.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void testExists() {
        userRepository.addUser("user1", "password1", Role.USER, "User One");

        assertThat(userRepository.exists("user1")).isTrue();
        assertThat(userRepository.exists("nonexistent")).isFalse();
    }

    @Test
    void testMatches() {
        userRepository.addUser("user1", "password1", Role.USER, "User One");
        assertThat(userRepository.matches("user1", "password1")).isTrue();
        assertThat(userRepository.matches("user1", "wrongpassword")).isFalse();
    }

    @Test
    void testAddUser() {
        User user = userRepository.addUser("user1", "password1", Role.USER, "User One");
        assertThat(user.getId()).isEqualTo("user1");
        assertThat(user.getName()).isEqualTo("User One");
    }

    @Test
    void testAddUserAlreadyExists() {
        // 첫 번째 사용자 추가
        userRepository.addUser("user1", "password1", Role.USER, "User One");

        // 두 번째 사용자 추가 시도 - 같은 ID로 추가 시도
        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userRepository.addUser("user1", "password2", Role.USER, "User Two");
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).contains("'user1' 아이디를 가진 사용자가 이미 존재합니다.");
    }

}
