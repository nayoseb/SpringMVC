package com.nhnacademy.nhnmart.controller;


import com.nhnacademy.nhnmart.reposiyory.UserRepository;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("/cs")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String login(@CookieValue(value = "SESSION", required = false) String session,
                        Model model) {
        if (StringUtils.hasText(session)) {

            model.addAttribute("id", session);
            return "redirect:/";
        } else {
            return "loginForm";
        }
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          HttpServletResponse response, ModelMap modelMap) {
        if (userRepository.matches(username, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("SESSION", "on");
            session.setAttribute("username", username);

            return "redirect:/cs/inquiries";

        } else {
            log.info("Login failed for username: {}", username);
            modelMap.put("loginError", "Invalid username or password");
            return "loginForm"; // 로그인 실패 시 다시 로그인 폼으로
        }
    }

}