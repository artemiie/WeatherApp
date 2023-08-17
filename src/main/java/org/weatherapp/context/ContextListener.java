package org.weatherapp.context;

import org.weatherapp.dao.SessionDao;
import org.weatherapp.dao.UserDao;
import org.weatherapp.dao.UserLocationDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener {
    private AtomicReference<SessionDao> sessionDao;
    private AtomicReference<UserDao> userDao;
    private AtomicReference<UserLocationDao> userLocationDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        sessionDao = new AtomicReference<>(new SessionDao());
        userDao = new AtomicReference<>(new UserDao());
        userLocationDao = new AtomicReference<>(new UserLocationDao());
        final ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("userDao", userDao);
        servletContext.setAttribute("sessionDao", sessionDao);
        servletContext.setAttribute("userLocationDao", userLocationDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        userDao = null;
        sessionDao = null;
        userLocationDao = null;
    }
}