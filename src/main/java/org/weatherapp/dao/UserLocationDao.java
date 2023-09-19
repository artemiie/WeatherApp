package org.weatherapp.dao;

import org.hibernate.Session;
import org.weatherapp.model.UserLocation;

import java.util.List;

public class UserLocationDao {
    public static Long save(UserLocation userLocation) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        Long savedUserLocationId = null;
        try {
            session.beginTransaction();
            savedUserLocationId = (Long) session.save(userLocation);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return savedUserLocationId;
    }

    public static List<UserLocation> getByUserId(Long userId) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<UserLocation> userLocationList = session.createQuery("from UserLocation where userId = '" + userId + "'").getResultList();
            session.getTransaction().commit();
            return userLocationList;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public static UserLocation getById(Long locationId) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            UserLocation userLocation = session.get(UserLocation.class,locationId);
            session.getTransaction().commit();
            return userLocation;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }
}
