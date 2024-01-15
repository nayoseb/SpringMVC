package com.nhnacademy.nhnmart.interceptor;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private static final String SESSION = "SESSION";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession httpSession = request.getSession(false);
        if (Objects.isNull(httpSession) || httpSession.getAttribute(SESSION) == null) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
