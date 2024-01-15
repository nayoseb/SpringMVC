package com.nhnacademy.nhnmart.interceptor;

import com.nhnacademy.nhnmart.domain.Role;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleAdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession httpSession = request.getSession(false);

        if (Objects.isNull(httpSession)) {
            response.sendRedirect("/cs/login");
            return false;
        }

        String sessionRoleString = (String) httpSession.getAttribute("username");
        if (Objects.isNull(sessionRoleString)) {
            response.sendRedirect("/cs/login");
            return false;
        }

        Role sessionRole;
        try {
            sessionRole = Role.valueOf(sessionRoleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            response.sendRedirect("/cs");
            return false;
        }

        if (sessionRole != Role.ADMIN) {
            response.sendRedirect("/cs");
            return false;
        }

        return true;
    }
}
