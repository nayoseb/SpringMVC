package com.nhnacademy.nhnmart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.nhnmart.reposiyory.UserRepository;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void testLoginWithNoSession() throws Exception {
        mockMvc.perform(get("/cs/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    void testLoginWithSession() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SESSION", "on");
        mockMvc.perform(get("/cs/login").cookie(new Cookie("SESSION", "on")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?id=on"));


    }


    @Test
    void testDoLoginSuccess() throws Exception {
        when(userRepository.matches("validUser", "validPass")).thenReturn(true);

        mockMvc.perform(post("/cs/login")
                        .param("username", "validUser")
                        .param("password", "validPass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/inquiries"));
    }

    @Test
    void testDoLoginFailure() throws Exception {
        when(userRepository.matches("invalidUser", "invalidPass")).thenReturn(false);

        mockMvc.perform(post("/cs/login")
                        .param("username", "invalidUser")
                        .param("password", "invalidPass"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"))
                .andExpect(model().attributeExists("loginError"));
    }

}
