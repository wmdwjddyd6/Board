package com.min.board.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "tb_userinfo")
@IdClass(MemberID.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Id
    @NotNull(message = "username을 입력하세요.")
    @Size(min = 1, max = 20, message = "username은 1자 이상 20자 이하입니다.")
    private String username;

    @NotNull(message = "암호를 입력하세요.")
    @Size(min = 6, max = 100, message = "암호는 6자 이상 100자 이하 입니다.")
    private String password;

    @NotNull
    @Size(min = 6, max = 45, message = "이메일은 6자 이상 45자 이하 입니다.")
    @Email
    private String email;

    @Column
    private String role;

//    @CreationTimestamp
//    private Timestamp createDate;
}
