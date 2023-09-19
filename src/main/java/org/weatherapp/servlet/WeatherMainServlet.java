package org.weatherapp.servlet;

import org.weatherapp.dao.DaoConfiguration;
import org.weatherapp.exception.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.weatherapp.util.Utils.getCookieByName;
import static org.weatherapp.util.Utils.isUserSessionActive;

public abstract class WeatherMainServlet extends HttpServlet {
    @Override
    public void init() {
        DaoConfiguration.getSessionFactory();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cookie cookie = getCookieByName(req.getCookies(), "sessionId");
            req.setAttribute("isUserSessionActive", isUserSessionActive(cookie));
            super.service(req, resp);
        } catch (LoginParametersException | UserNotLoggedException | SearchParametersException |
                 NotValidLocationParamatersException |
                 CookieNotFoundException e) {
            req.getRequestDispatcher("/WEB-INF" + req.getServletPath() + ".jsp").forward(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }
}
