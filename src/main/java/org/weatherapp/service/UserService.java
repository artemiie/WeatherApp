package org.weatherapp.service;

import org.weatherapp.dao.UserDao;
import org.weatherapp.model.User;

public class UserService {
    public static void save(User user) {
        UserDao.save(user);
    }

    public static User findById(int userId) {
        return UserDao.getById(userId);
    }

    public static User findByLogin(String login) throws Exception {
        var userList = UserDao.getByLogin(login);
        if (userList == null || userList.isEmpty()) {
            throw new Exception("No user was found.");
        }
        return userList.get(0);
    }
}
