package com.ralph.mydashbord.model;


import java.util.ArrayList;
import java.util.List;

public class LoginResponse {

    private String username;
    private List<Users> authorities = new ArrayList<>();
    private String tokenType;
    private String accessToken;

    public String getUsername() {
        return username;
    }

    public List<Users> getAuthorities() {
        return authorities;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(List<Users> authorities) {
        this.authorities = authorities;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "username='" + username + '\'' +
                ", authorities=" + authorities +
                ", tokenType='" + tokenType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
