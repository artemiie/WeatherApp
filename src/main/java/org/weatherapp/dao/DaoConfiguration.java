package org.weatherapp.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.weatherapp.model.User;
import org.weatherapp.model.UserLocation;
import org.weatherapp.model.UserSession;

public class DaoConfiguration {
    private DaoConfiguration() {
    }

    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserSession.class)
            .addAnnotatedClass(UserLocation.class)
            .buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
