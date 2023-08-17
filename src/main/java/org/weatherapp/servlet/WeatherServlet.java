package org.weatherapp.servlet;

import org.weatherapp.dao.SessionDao;
import org.weatherapp.dao.UserDao;
import org.weatherapp.dao.UserLocationDao;
import org.weatherapp.model.Session;
import org.weatherapp.model.User;
import org.weatherapp.model.weather.Weather;
import org.weatherapp.service.WeatherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.weatherapp.util.Utils.getCookieByName;

@WebServlet(urlPatterns = {"/weather"})
public class WeatherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/app/search");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String lat = req.getParameter("lat");
        final String lon = req.getParameter("lon");
        final String country = req.getParameter("country");
        final String state = req.getParameter("state");
        final String city = req.getParameter("city");

        boolean isSubscriptionActive = checkIfSubscriptionisActive(req, resp, lat, lon);

        WeatherService weatherService = new WeatherService();
        Weather weatherForecast = weatherService.getWeatherForecast(lat, lon);

        req.setAttribute("weather", weatherForecast);
        req.setAttribute("country", country);
        req.setAttribute("state", state);
        req.setAttribute("city", city);
        req.setAttribute("lat", lat);
        req.setAttribute("lon", lon);
        req.setAttribute("isSubscriptionActive", isSubscriptionActive);
        req.getRequestDispatcher("/WEB-INF/weather.jsp").forward(req, resp);
    }

    private boolean checkIfSubscriptionisActive(HttpServletRequest req, HttpServletResponse resp, String lat, String lon) throws ServletException, IOException {
        boolean isUserLogged = (boolean) req.getAttribute("isUserLogged");
        if (!isUserLogged) {
            return false;
        }
        User user = getLoggedInUser(req, resp);
        @SuppressWarnings("unchecked") final AtomicReference<UserLocationDao> userLocationDao = (AtomicReference<UserLocationDao>) req.getServletContext().getAttribute("userLocationDao");
        return userLocationDao.get().getByUserId(user.getUserId()).stream().anyMatch(userLocation -> userLocation.getLat().equals(lat) && userLocation.getLon().equals(lon));
    }

    private User getLoggedInUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @SuppressWarnings("unchecked") final AtomicReference<SessionDao> sessionDao = (AtomicReference<SessionDao>) req.getServletContext().getAttribute("sessionDao");
        @SuppressWarnings("unchecked") final AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userDao");

        Cookie cookie = null;
        Session session = null;
        User user = null;
        try {
            cookie = getCookieByName(req.getCookies(), "sessionId");
            session = sessionDao.get().getById(cookie.getValue());
            user = userDao.get().getById(session.getUserId());
        } catch (Exception e) {
            req.setAttribute("isUserLogged", false);
            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            throw new RuntimeException("User is not logged in.", e);
        }
        return user;
    }
}