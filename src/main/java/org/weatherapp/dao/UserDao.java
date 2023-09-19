package org.weatherapp.dao;

import org.hibernate.Session;
import org.weatherapp.model.User;

import java.util.List;

public class UserDao {
    public static Long save(User user) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Long savedUserId = (Long) session.save(user);
            session.getTransaction().commit();
            return savedUserId;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public static User getById(Long userId) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, userId);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public static List<User> getByLogin(String login) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<User> userList = session.createQuery("from User where login = '" + login + "'").getResultList();
            session.getTransaction().commit();
            return userList;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }
}
