package org.weatherapp.util;

import org.apache.commons.lang3.StringUtils;
import org.weatherapp.dao.SessionDao;
import org.weatherapp.dao.UserDao;
import org.weatherapp.model.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicReference;

public class Utils {
    public static Cookie getCookieByName(Cookie[] cookies, String name) throws Exception {
        if (cookies == null) {
            throw new Exception("User is not logged in.");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        throw new Exception("User is not logged in.");
    }

    public static boolean isValid(String txt) {
        return !StringUtils.isEmpty(txt) && !StringUtils.isBlank(txt);
    }

    public static boolean isUserLogged(HttpServletRequest request, Cookie cookie) {
        @SuppressWarnings("unchecked") final AtomicReference<SessionDao> sessionDao = (AtomicReference<SessionDao>) request.getServletContext().getAttribute("sessionDao");
        @SuppressWarnings("unchecked") final AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) request.getServletContext().getAttribute("userDao");

        if (cookie == null || cookie.getValue() == null || cookie.getValue().isEmpty() || cookie.getValue().isBlank()) {
            return false;
        }

        String sessionId = cookie.getValue();
        Session session;
        try {
            session = sessionDao.get().getById(sessionId);
        } catch (Exception e) {
            return false;
        }


        try {
            userDao.get().getById(session.getUserId());
        } catch (Exception e) {
            sessionDao.get().delete(sessionId);
            return false;
        }

        return true;
    }

}
