package org.weatherapp.servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.weatherapp.model.User;
import org.weatherapp.model.UserLocation;
import org.weatherapp.model.UserSession;
import org.weatherapp.service.UserLocationService;
import org.weatherapp.service.UserService;
import org.weatherapp.service.UserSessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.weatherapp.util.Utils.getCookieByName;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkIfUserIsLoggedIn(req, resp);
        User user = getLoggedInUser(req, resp);
        loadProfilePageForLoggedInUser(req, resp, user);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkIfUserIsLoggedIn(req, resp);
        User user = getLoggedInUser(req, resp);
        addLocationsToUser(req, resp, user);
        loadProfilePageForLoggedInUser(req, resp, user);
    }

    private void checkIfUserIsLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isUserSessionActive = (boolean) req.getAttribute("isUserSessionActive");
        if (!isUserSessionActive) {
            req.setAttribute("isUserSessionActive", false);
            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            throw new RuntimeException("User is not logged in.");
        }
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

    public void loadProfilePageForLoggedInUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<UserLocation> userLocationList = UserLocationService.findByUserId(user.getUserId());
        req.setAttribute("user", user);
        req.setAttribute("userLocations", userLocationList);
        req.setAttribute("isUserSessionActive", true);
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }

    private void addLocationsToUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        final String lat = req.getParameter("lat");
        final String lon = req.getParameter("lon");
        final String city = req.getParameter("city");
        final String country = req.getParameter("country");
        final String state = req.getParameter("state");

        if (!NumberUtils.isCreatable(lat) || !NumberUtils.isCreatable(lon) || StringUtils.isEmpty(city) || StringUtils.isEmpty(country)) {
            req.setAttribute("subscribeOnLocationError", true);
            req.setAttribute("subscribeOnLocationErrorMsg", "Something went wrong, please try again.");
            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            throw new RuntimeException("Some of these values may not be correct: LAT=[" + lat + "], LON=[" + lon + "], CITY=[" + city + "], COUNTRY=[" + country + "].");
        }

        UserLocation userLocation = new UserLocation(user.getUserId(), lat, lon, city, country, state);
        UserLocationService.save(userLocation);
    }

}