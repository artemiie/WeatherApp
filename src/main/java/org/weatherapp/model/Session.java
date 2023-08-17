package org.weatherapp.model;

import java.time.LocalDateTime;

public class Session {
    private String sessionId;
    private String userId;
    private LocalDateTime expirationDate;

    public Session() {
    }

    public Session(String sessionId, String userId, LocalDateTime expirationDate) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.expirationDate = expirationDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
