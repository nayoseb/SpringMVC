package com.nhnacademy.nhnmart.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private String id;
    private String password;
    private Role role;
    private String name;


    @Builder
    public User(String id, String password, Role role, String name) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.name = name;
    }
}
