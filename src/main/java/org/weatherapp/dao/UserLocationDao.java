package org.weatherapp.dao;

import org.hibernate.Session;
import org.weatherapp.model.UserLocation;

import java.util.List;

public class UserLocationDao {
    public static void save(UserLocation userLocation) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(userLocation);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public static List<UserLocation> getByUserId(int userId) {
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
}
