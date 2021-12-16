package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.repository.MemberRepository;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(Member member){
        memberRepository.save(member);

        return member.getUsername(); //test
    }
}
