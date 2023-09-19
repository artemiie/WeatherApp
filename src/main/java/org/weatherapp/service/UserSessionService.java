package org.weatherapp.service;

import org.weatherapp.dao.UserSessionDao;
import org.weatherapp.model.UserSession;

public class UserSessionService {
    public static void save(UserSession userSession) {
        UserSessionDao.save(userSession);
    }

    public static UserSession find(String sessionId){
        return UserSessionDao.find(sessionId);
    }

    public static void delete(String sessionId) {
        UserSessionDao.delete(sessionId);
    }
}
