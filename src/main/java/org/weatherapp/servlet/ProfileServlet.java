package org.weatherapp.servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.weatherapp.dao.SessionDao;
import org.weatherapp.dao.UserDao;
import org.weatherapp.dao.UserLocationDao;
import org.weatherapp.model.Session;
import org.weatherapp.model.User;
import org.weatherapp.model.UserLocation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        boolean isUserLogged = (boolean) req.getAttribute("isUserLogged");
        if (!isUserLogged) {
            req.setAttribute("isUserLogged", false);
            req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            throw new RuntimeException("User is not logged in.");
        }
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

    public void loadProfilePageForLoggedInUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        @SuppressWarnings("unchecked") final AtomicReference<UserLocationDao> userLocationDao = (AtomicReference<UserLocationDao>) req.getServletContext().getAttribute("userLocationDao");
        List<UserLocation> userLocationList = userLocationDao.get().getByUserId(user.getUserId());
        req.setAttribute("user", user);
        req.setAttribute("userLocations", userLocationList);
        req.setAttribute("isUserLogged", true);
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }

    private void addLocationsToUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        @SuppressWarnings("unchecked") final AtomicReference<UserLocationDao> userLocationDao = (AtomicReference<UserLocationDao>) req.getServletContext().getAttribute("userLocationDao");

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
        userLocationDao.get().save(userLocation);
    }

}