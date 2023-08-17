package org.weatherapp.dao;

import org.weatherapp.model.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionDao {
    private final List<Session> store = new ArrayList<>();

    public Session getById(final String id) throws Exception {
        for (Session session : store) {
            if (session.getSessionId().equals(id)) {
                if (session.getExpirationDate().isBefore(LocalDateTime.now())) {
                    delete(id);
                    throw new Exception("You must log in.");
                }
                return session;
            }
        }
        throw new Exception("No such user exists.");
    }

    public boolean save(final Session newSession) {
        for (Session session : store) {
            if (session.getSessionId().equals(newSession.getSessionId())) {
                return false;
            }
        }
        return store.add(newSession);
    }

    public boolean delete(final String id) {
        for (Session session : store) {
            if (session.getSessionId().equals(id)) {
                store.remove(session);
                return true;
            }
        }
        return false;
    }
}