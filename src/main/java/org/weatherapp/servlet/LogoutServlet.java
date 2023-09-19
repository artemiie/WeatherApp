package org.weatherapp.servlet;

import org.weatherapp.exception.CookieNotFoundException;
import org.weatherapp.service.UserSessionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.weatherapp.util.Utils.getCookieByName;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Cookie cookie = getCookieByName(req.getCookies(), "sessionId");
            UserSessionService.delete(cookie.getValue());
        } catch (CookieNotFoundException ignored) {}

        Cookie emptyCookie = new Cookie("sessionId", null);
        emptyCookie.setMaxAge(0);

        resp.addCookie(emptyCookie);

        resp.sendRedirect("/app/home");
    }
}