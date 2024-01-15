package com.nhnacademy.nhnmart.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession httpSession = request.getSession();

        String username = (String) httpSession.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
        }
        return "index";
    }


}
