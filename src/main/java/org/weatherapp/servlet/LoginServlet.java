package org.weatherapp.servlet;

import com.password4j.Password;
import org.weatherapp.dao.SessionDao;
import org.weatherapp.dao.UserDao;
import org.weatherapp.model.Session;
import org.weatherapp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.weatherapp.util.Utils.isValid;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<String, String>();
        req.setAttribute("errors", errors);

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        //check if values are valid
        if (!isValid(login)) errors.put("login", "Please enter a valid login");
        if (!isValid(password)) errors.put("password", "Please enter a valid password");

        //report errors if any
        if (!errors.isEmpty()) {
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }

        @SuppressWarnings("unchecked") final AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userDao");

        //get user if exists
        User user = null;
        try {
            user = userDao.get().getByLogin(login);
        } catch (Exception e) {
            errors.put("noSuchUser", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }

        //password validity check
        if (!Password.check(password, user.getPassword()).withBcrypt()) {
            errors.put("password", "Wrong password!");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }

        //create new session for 24h for logged in user
        @SuppressWarnings("unchecked") final AtomicReference<SessionDao> sessionDao = (AtomicReference<SessionDao>) req.getServletContext().getAttribute("sessionDao");
        Session session = new Session(UUID.randomUUID().toString(), user.getUserId(), LocalDateTime.now().plusHours(24));
        sessionDao.get().save(session);

        //add cookie with sessionId
        Cookie newCookie = new Cookie("sessionId", session.getSessionId());
        resp.addCookie(newCookie);

        resp.sendRedirect("/app/profile");
    }
}