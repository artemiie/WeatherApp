package org.weatherapp.servlet;

import com.password4j.Hash;
import com.password4j.Password;
import org.weatherapp.model.User;
import org.weatherapp.model.UserSession;
import org.weatherapp.service.UserService;
import org.weatherapp.service.UserSessionService;

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

import static org.weatherapp.util.Utils.isValid;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<String, String>();
        req.setAttribute("errors", errors);

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final String name = req.getParameter("name");
        //final int userId = UUID.randomUUID().toString();
        final String sessionId = UUID.randomUUID().toString();

        if (!isValid(login)) errors.put("login", "Please enter a valid login");
        if (!isValid(password)) errors.put("password", "Please enter a valid password");
        if (!isValid(name)) errors.put("name", "Please enter a valid name");

        if (!errors.isEmpty()) {
            loadRegisterPage(req, resp);
            return;
        }

        Hash hash = Password.hash(password).withBcrypt();
        User newUser = new User(login, hash.getResult(), name, LocalDateTime.now());
        try {
            UserService.save(newUser);
        } catch (Exception e) {
            errors.put("userexist", "Such user already exists.");
            loadRegisterPage(req, resp);
            return;
        }

        UserSession userSession = new UserSession(sessionId, newUser.getUserId(), LocalDateTime.now().plusHours(24));

        UserSessionService.save(userSession);

        req.getSession().setAttribute("user",newUser);

        Cookie newCookie = new Cookie("sessionId", userSession.getSessionId());
        resp.addCookie(newCookie);
        resp.sendRedirect("/app/profile");
    }

    private void loadRegisterPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isUserSessionActive", false);
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

}