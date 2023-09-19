package org.weatherapp.service;

import org.weatherapp.dao.UserLocationDao;
import org.weatherapp.model.UserLocation;

import java.util.List;

public class UserLocationService {

    public static UserLocation findById(Long userLocationId) throws Exception {
        if (userLocationId == null) {
            throw new Exception("");
        }
        UserLocation userLocation = UserLocationDao.getById(userLocationId);
        if (userLocation == null) {
            throw new Exception("");
        }
        return userLocation;
    }

    public static List<UserLocation> findByUserId(Long userId) {
        return UserLocationDao.getByUserId(userId);
    }

    public static void save(UserLocation userLocation) {
        Long savedUserLocationId = UserLocationDao.save(userLocation);
        try {
            findById(savedUserLocationId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
