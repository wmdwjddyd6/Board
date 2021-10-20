package com.min.board.controller;

public class MemberForm {

    private String userName;
    private String userId;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

}
