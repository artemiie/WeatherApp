package org.weatherapp.servlet;

import org.weatherapp.dao.SessionDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.weatherapp.util.Utils.getCookieByName;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie cookie = null;
        try {
            cookie = getCookieByName(req.getCookies(), "sessionId");
        } catch (Exception e) {
            return;
        }
        @SuppressWarnings("unchecked") final AtomicReference<SessionDao> dao = (AtomicReference<SessionDao>) req.getServletContext().getAttribute("sessionDao");
        dao.get().delete(cookie.getValue());
        Cookie emptyCookie = new Cookie("sessionId", null);
        emptyCookie.setMaxAge(0);
        resp.addCookie(emptyCookie);
        resp.sendRedirect("/app/home");
    }
}