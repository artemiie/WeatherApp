package org.weatherapp.servlet;

import com.password4j.Hash;
import com.password4j.Password;
import org.weatherapp.exception.RegisterParametersException;
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

import static org.weatherapp.util.Utils.isStringNotNullNotEmptyNotBlank;

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
        final String sessionId = UUID.randomUUID().toString();

        checkCredentials(login, password, name, errors);

        //report errors if any
        if (!errors.isEmpty()) {
            throw new RegisterParametersException("Register credentials can't be empty or blank.");
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

        req.getSession().setAttribute("user", newUser);

        Cookie newCookie = new Cookie("sessionId", userSession.getSessionId());
        resp.addCookie(newCookie);
        resp.sendRedirect("/app/profile");
    }

    private void loadRegisterPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isUserSessionActive", false);
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

    private void checkCredentials(String login, String password, String name, Map<String, String> errors) {
        if (!isStringNotNullNotEmptyNotBlank(login)) errors.put("login", "Login is not valid");
        if (!isStringNotNullNotEmptyNotBlank(password)) errors.put("password", "Password is not valid.");
        if (!isStringNotNullNotEmptyNotBlank(name)) errors.put("name", "Name is not valid.");
    }

}