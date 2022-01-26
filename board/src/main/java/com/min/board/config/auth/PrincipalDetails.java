package com.min.board.config.auth;

import com.min.board.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
    시큐리티가 로그인을 진행시키면 실행
    로그인 진행이 완료되면 시큐리티 session을 생성 (Security ContextHolder라는 키 값으로 세션 정보 저장)
    키에 대한 값으로 들어가는 오브젝트 타입 : Authentication
    Authentication 안에 User(UserDetails타입) 정보가 있어야 됨.

    요약 : Security Session 안에 => Authentication객체 안에 => UserDetails(PrincipalDetails)
 */

public class PrincipalDetails implements UserDetails {

    private Member member; // 콤포지션

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // 해당 유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    // 계정이 일정 기간 만료는지 체크
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있는지 체크
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 너무 오래 사용했는지 체크
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 됐는지 체크 (~년 동안 로그인 안하면 휴면계정 등)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
