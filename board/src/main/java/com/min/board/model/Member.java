package com.min.board.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_userinfo")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "username을 입력하세요.")
    @Size(min = 1, max = 20, message = "username은 1자 이상 20자 이하입니다.")
    private String username;

    @NotNull(message = "암호를 입력하세요.")
    @Size(min = 6, max = 100, message = "암호는 6자 이상 100자 이하 입니다.")
    private String password;

    @NotNull
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
