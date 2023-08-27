package org.weatherapp.service;

import org.weatherapp.dao.UserLocationDao;
import org.weatherapp.model.UserLocation;

import java.util.List;

public class UserLocationService {
    public static void save(UserLocation userLocation) {
        UserLocationDao.save(userLocation);
    }

    public static List<UserLocation> findByUserId(int userId) {
        return UserLocationDao.getByUserId(userId);
    }
}
