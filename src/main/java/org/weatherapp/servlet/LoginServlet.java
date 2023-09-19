package org.weatherapp.servlet;

import com.password4j.Password;
import org.weatherapp.exception.LoginParametersException;
import org.weatherapp.exception.UserNotFoundException;
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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws LoginParametersException, IOException, UserNotFoundException {
        Map<String, String> errors = new HashMap<String, String>();
        req.setAttribute("errors", errors);

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        //check if values are valid
        checkCredentials(login, password, errors);

        //report errors if any
        if (!errors.isEmpty()) {
            throw new LoginParametersException("Login credentials can't be empty or blank.");
        }

        //get user if exists
        User user = null;
        try {
            user = UserService.findByLogin(login);
        } catch (UserNotFoundException e) {
            errors.put("login", "User " + login + " not found.");
            throw e;
        }

        //password validity check
        if (!Password.check(password, user.getPassword()).withBcrypt()) {
            errors.put("password", "Wrong password.");
            throw new LoginParametersException("The entered password does not match an existing user password.");
        }

        String newSessionId = UUID.randomUUID().toString();

        //create new session for 24h for logged in user
        createNewUserSession(newSessionId, user.getUserId(), LocalDateTime.now().plusHours(24));

        //add cookie with sessionId
        addNewCookieToHttpServletResponse("sessionId", newSessionId, resp);

        resp.sendRedirect("/app/profile");
    }

    private void checkCredentials(String login, String password, Map<String, String> errors) {
        if (!isStringNotNullNotEmptyNotBlank(login)) errors.put("login", "Login is not valid.");
        if (!isStringNotNullNotEmptyNotBlank(password)) errors.put("password", "Password is not valid.");
    }

    private void createNewUserSession(String sessiondId, Long userId, LocalDateTime expirationDateTime) {
        UserSession userSession = new UserSession(sessiondId, userId, expirationDateTime);
        UserSessionService.save(userSession);
    }

    private void addNewCookieToHttpServletResponse(String cookieName, String cookieValue, HttpServletResponse response) {
        Cookie newCookie = new Cookie(cookieName, cookieValue);
        response.addCookie(newCookie);
    }
}
