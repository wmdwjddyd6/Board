package com.min.board.config.auth;

import com.min.board.model.MemberDto;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
* 
* 로그인 처리
* /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
* 
* */
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    // 리턴되는 순간 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto memberDtoEntity = memberService.getMember(username);

        if(memberDtoEntity != null) {
            return new PrincipalDetails(memberDtoEntity);
        }
        return null;
    }
}
