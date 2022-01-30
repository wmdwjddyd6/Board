package com.min.board.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
public class MemberDto {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$", message = "username은 영문(소문자)과 숫자만 가능합니다.")
    @Size(min = 5, max = 20, message = "username은 5자 이상 20자 이하입니다.")
    private String username;

    @NotNull
    @Size(min = 6, max = 30, message = "암호는 6자 이상 30자 이하 입니다.")
    private String password;

    @NotNull
    @Size(min = 6, max = 45, message = "올바른 이메일을 입력하세요.")
    private String email;
    private String role;
    private Timestamp createDate;
}
