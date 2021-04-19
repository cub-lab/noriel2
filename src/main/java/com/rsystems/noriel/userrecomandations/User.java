package com.rsystems.noriel.userrecomandations;

public class User {
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                '}';
    }
}
