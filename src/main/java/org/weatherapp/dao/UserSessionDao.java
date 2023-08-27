package org.weatherapp.dao;

import org.hibernate.Session;
import org.weatherapp.model.UserSession;

public class UserSessionDao {
    public static void save(UserSession userSession) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(userSession);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public static UserSession find(String sessionId) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            UserSession userSession = session.get(UserSession.class, sessionId);
            session.getTransaction().commit();
            return userSession;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    public static void delete(String sessionId) {
        Session session = DaoConfiguration.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createQuery("delete UserSession where sessionId ='" + sessionId + "'").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }
}
