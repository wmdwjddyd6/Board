package com.min.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationFailureHandler customFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/board/**", "/account/**", "/trash/**", "/comments/**").authenticated() // antMatchers에 적힌 주소는 로그인 필요
//                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 권한 설정
                    .anyRequest().permitAll() // 그 외 요청은 허용
                    .and()
                .formLogin()
                    .loginPage("/loginForm") // 로그인 페이지
                    .loginProcessingUrl("/login") // 시큐리티에서 로그인을 대신 진행하도록 정의 -> 컨트롤러에 안만들어도 됨.
                    .defaultSuccessUrl("/")
                    .failureForwardUrl("/fail_login")
                    .and()
                .logout()
                    .permitAll();
    }
}

