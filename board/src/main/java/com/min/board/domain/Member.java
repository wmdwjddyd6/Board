package com.min.board.domain;

public class Member {

    private String userName;
    private String userId;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "userName : " + userName
                + " ID : " + userId
                + " passwd : " + password;
    }
}
