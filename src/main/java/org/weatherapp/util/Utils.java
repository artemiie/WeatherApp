package org.weatherapp.util;

import org.apache.commons.lang3.StringUtils;
import org.weatherapp.exception.CookieNotFoundException;
import org.weatherapp.exception.UserNotLoggedException;
import org.weatherapp.model.User;
import org.weatherapp.model.UserSession;
import org.weatherapp.service.UserService;
import org.weatherapp.service.UserSessionService;

import javax.servlet.http.Cookie;

public class Utils {
    public static Cookie getCookieByName(Cookie[] cookies, String cookieName) throws CookieNotFoundException {
        if (cookies == null) {
            throw new CookieNotFoundException("Cookie with name \""+cookieName+"\" not found.");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        throw new CookieNotFoundException("Cookie with name \""+cookieName+"\" not found.");
    }

    public static boolean isStringNotNullNotEmptyNotBlank(String txt) {
        return !StringUtils.isEmpty(txt) && !StringUtils.isBlank(txt);
    }

    public static boolean isUserSessionActive(Cookie cookie) throws UserNotLoggedException {
        if (cookie == null || !isStringNotNullNotEmptyNotBlank(cookie.getValue())) {
            return false;
        }

        String sessionId = cookie.getValue();
        UserSession userSessionOnDb = UserSessionService.find(sessionId);
        if (userSessionOnDb == null) {
            return false;
        }

        User userOnDb = UserService.findById(userSessionOnDb.getUserId());
        if (userOnDb == null) {
            return false;
        }

        return true;
    }

}
