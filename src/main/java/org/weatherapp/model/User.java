package org.weatherapp.model;

public class User {
    private String login;
    private String password;
    private String name;
    private String userId;
    private Location location;

    public User() {
    }

    public User(String login, String password, String name, String userId) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
