package com.min.board.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    // 메일을 통해 임시 비밀번호 전송
    public void sendMail(String username, String userEmail, String temporaryPassword) {
        List<String> toUserList = new ArrayList<>();
        toUserList.add(userEmail);
        int userSize = toUserList.size();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo((String[]) toUserList.toArray(new String[userSize]));
        simpleMailMessage.setSubject("임시 비밀번호를 보내드립니다.");
        simpleMailMessage.setText(username + "님의 임시 비밀번호 : " + temporaryPassword);

        javaMailSender.send(simpleMailMessage);
        logger.debug("{}님에게 {}로 임시 비밀번호를 보냈습니다.", username, userEmail);
    }
}
