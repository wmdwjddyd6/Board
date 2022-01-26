package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MailService;
import com.min.board.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
*
* 로그인 클릭 시 나오는 메뉴들 관련 컨트롤러
* [로그인, 아이디/비밀번호 찾기]
*
 */
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailService mailService;

    // 로그인 폼
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        model.addAttribute("error", error);
        return "account/loginForm";
    }

    // 로그인 실패
    @PostMapping("/fail_login")
    public String handleFailedLogin() {
        logger.debug("로그인에 실패했습니다.");
        return "redirect:/loginForm?error=true";
    }

    // ID 찾기 화면 진입 및 Email에 매치되는 ID 보여주기
    @GetMapping("/findIdForm")
    public String findIdForm(@RequestParam(required = false) String email, Model model) throws Exception {
        if(email != null) { // email을 입력한 경우 
            List<Member> memberList = memberService.getMemberByEmail(email); // 해당 이메일에 등록된 ID를
            model.addAttribute("memberList", memberList); // 클라이언트로 리턴
        }
        return "account/findIdForm";
    }

    // ID 찾기
    @PostMapping("/findId")
    public String findId(@RequestParam(required = false) String email) throws Exception {
        if(memberService.checkEmail(email)) {
            return "redirect:/findIdForm?email=" + email;
        } else {
            return "redirect:/findIdForm?error=true";
        }
    }

    // PW 찾기 폼 진입
    @GetMapping("/passwordResetForm")
    public String passwordResetForm() {
        return "account/passwordResetForm";
    }

    // PW 리셋 & 비밀번호 이메일 전송
    @PostMapping("/passwordReset")
    public String passwordReset(String username, String email) throws Exception {
        if(username.isEmpty() || email.isEmpty()) { // 빈 입력값인지 체크
            logger.debug("username 또는 email을 입력하지 않았습니다.");
            return "redirect:/passwordResetForm?error=true";
        } else if(memberService.compareEmailUsername(username, email)) { // username과 email이 매치되는지 체크
            String temporaryPassword = memberService.getRandomPassword(username); // 임시 패스워드 발급
            mailService.sendMail(username, email, temporaryPassword); // email로 임시 패스워드 전송

            return "redirect:/passwordResetForm?email=" + email;
        } else {
            return "redirect:/passwordResetForm?error=true";
        }
    }
}
