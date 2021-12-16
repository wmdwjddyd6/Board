//package com.min.board.config;
//
//import com.min.board.repository.BoardRepository;
//import com.min.board.repository.MemberRepository;
//import com.min.board.repository.MyMemberRepository;
//import com.min.board.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    private final DataSource dataSource;
//
//    @Autowired
//    public DataSourceConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//
//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }
//
//    @Bean
//    public MemberRepository memberRepository() {
//        return new MyMemberRepository(dataSource);
//    }
//}
