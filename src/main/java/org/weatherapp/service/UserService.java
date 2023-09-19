package org.weatherapp.service;

import org.weatherapp.dao.UserDao;
import org.weatherapp.dao.UserLocationDao;
import org.weatherapp.exception.UserNotFoundException;
import org.weatherapp.model.User;
import org.weatherapp.model.UserLocation;

public class UserService {
    public static void save(User user) {
        Long savedUserId = UserDao.save(user);
        findById(savedUserId);
    }

    public static User findById(Long userId) {
        return UserDao.getById(userId);
        if (userId == null) {
            throw new Exception("");
        }
        UserLocation userLocation = UserDao.getById(userId);
        if (userLocation == null) {
            throw new Exception("");
        }
        return userLocation;
    }

    public static User findByLogin(String login) throws UserNotFoundException {
        var userList = UserDao.getByLogin(login);
        if (userList == null || userList.isEmpty()) {
            throw new UserNotFoundException("User with login = [" + login + "] not found.");
        }
        return userList.get(0);
    }
}
