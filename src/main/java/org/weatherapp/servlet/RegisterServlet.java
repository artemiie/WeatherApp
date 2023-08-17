package org.weatherapp.servlet;
import com.password4j.Hash;
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
        final String userId = UUID.randomUUID().toString();
        final String sessionId = UUID.randomUUID().toString();

        if (!isValid(login)) errors.put("login", "Please enter a valid login");
        if (!isValid(password)) errors.put("password", "Please enter a valid password");
        if (!isValid(name)) errors.put("name", "Please enter a valid name");

        if (!errors.isEmpty()) {
            loadRegisterPage(req, resp);
            return;
        }

        @SuppressWarnings("unchecked") final AtomicReference<UserDao> dao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userDao");

        Hash hash = Password.hash(password).withBcrypt();

        boolean userIsSaved = dao.get().save(new User(login, hash.getResult(), name, userId));
        if (!userIsSaved) {
            errors.put("userexist", "Such user already exists.");
            loadRegisterPage(req, resp);
            return;
        }

        Session session = new Session(sessionId, userId, LocalDateTime.now().plusHours(24));

        @SuppressWarnings("unchecked") final AtomicReference<SessionDao> sessiondao = (AtomicReference<SessionDao>) req.getServletContext().getAttribute("sessionDao");
        sessiondao.get().save(session);

        Cookie newCookie = new Cookie("sessionId", session.getSessionId());
        resp.addCookie(newCookie);
        resp.sendRedirect("/app/profile");
    }

    private void loadRegisterPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isUserLogged", false);
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

}