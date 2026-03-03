package com.example.model;

/*
author: foo
 */

import java.io.Serializable;

public class UserSession implements Serializable {

    private String sessionId;
    private String userId;
    private long lastAccessTime;
    private boolean isActive;
    private transient String ipAddress;

    public UserSession(String sessionId, String userId, long lastAccessTime, boolean isActive, String ipAddress) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.lastAccessTime = lastAccessTime;
        this.isActive = isActive;
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                ", lastAccessTime=" + lastAccessTime +
                ", isActive=" + isActive +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
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

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
