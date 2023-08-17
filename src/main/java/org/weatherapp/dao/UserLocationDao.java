package org.weatherapp.dao;

import org.weatherapp.model.UserLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserLocationDao {
    private final List<UserLocation> store = new ArrayList<>();

    public List<UserLocation> getByUserId(final String userId) {
        return store.stream().filter(userLocation -> userLocation.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public boolean save(final UserLocation newLocation) {
        for (UserLocation u : store) {
            if (u.getUserId().equals(newLocation.getUserId()) && u.getLon().equals(newLocation.getLon()) && u.getLat().equals(newLocation.getLat())) {
                return false;
            }
        }
        return store.add(newLocation);
    }
}