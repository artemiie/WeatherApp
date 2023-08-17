package org.weatherapp.dao;
import org.weatherapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final List<User> store = new ArrayList<>();

    public User getByLogin(final String login) throws Exception {
        for (User user : store) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        throw new Exception("No such user exists.");
    }

    public User getById(final String id) throws Exception {
        for (User user : store) {
            if (user.getUserId().equals(id)) {
                return user;
            }
        }
        throw new Exception("No such user exists.");
    }

    public boolean save(final User user) {
        for (User u : store) {
            if (u.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        return store.add(user);
    }

    public boolean exists(final String login, final String password) {
        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}