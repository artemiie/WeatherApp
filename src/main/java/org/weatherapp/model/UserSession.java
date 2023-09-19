package org.weatherapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_session")
public class UserSession {
    @Id
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "expiry_datetime")
    private LocalDateTime expiryDate;

    public UserSession() {
    }

    public UserSession(String sessionId, Long userId, LocalDateTime expiryDate) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
