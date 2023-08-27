package org.weatherapp.util;

import org.apache.commons.lang3.StringUtils;
import org.weatherapp.model.UserSession;
import org.weatherapp.service.UserService;
import org.weatherapp.service.UserSessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
        if (cookie == null || cookie.getValue() == null || cookie.getValue().isEmpty() || cookie.getValue().isBlank()) {
            return false;
        }

        String sessionId = cookie.getValue();
        UserSession userSession;
        try {
            userSession = UserSessionService.find(sessionId);
        } catch (Exception e) {
            return false;
        }

        try {
            assert UserService.findById(userSession.getUserId()) != null;
        } catch (Exception e) {
            UserSessionService.delete(sessionId);
            return false;
        }

        return true;
    }

}
