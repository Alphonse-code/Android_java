package com.ralph.mydashbord.model;

public class RegisterResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
