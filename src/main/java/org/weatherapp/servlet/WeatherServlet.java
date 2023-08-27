package org.weatherapp.servlet;

import org.weatherapp.model.User;
import org.weatherapp.model.UserSession;
import org.weatherapp.model.weather.Weather;
import org.weatherapp.service.UserLocationService;
import org.weatherapp.service.UserService;
import org.weatherapp.service.UserSessionService;
import org.weatherapp.service.WeatherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        boolean isUserSessionActive = (boolean) req.getAttribute("isUserSessionActive");
        if (!isUserSessionActive) {
            return false;
        }
        User user = getLoggedInUser(req, resp);
        return UserLocationService.findByUserId(user.getUserId()).stream().anyMatch(userLocation -> userLocation.getLat().equals(lat) && userLocation.getLon().equals(lon));
    }

    private User getLoggedInUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = null;
        UserSession userSession = null;
        User user = null;
        try {
            cookie = getCookieByName(req.getCookies(), "sessionId");
            userSession = UserSessionService.find(cookie.getValue());
            user = UserService.findById(userSession.getUserId());
        } catch (Exception e) {
            req.setAttribute("isUserSessionActive", false);
            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            throw new RuntimeException("User is not logged in.", e);
        }
        return user;
    }
}