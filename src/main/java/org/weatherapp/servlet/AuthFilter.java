package org.weatherapp.servlet;

import org.weatherapp.dao.DaoConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.weatherapp.util.Utils.getCookieByName;
import static org.weatherapp.util.Utils.isUserLogged;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {
    @Override
    public void init() {
        DaoConfiguration.getSessionFactory();
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Cookie cookie = getCookieByName(request.getCookies(), "sessionId");
            request.setAttribute("isUserLogged", isUserLogged(request, cookie));
        } catch (Exception e) {
            request.setAttribute("isUserLogged", false);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
