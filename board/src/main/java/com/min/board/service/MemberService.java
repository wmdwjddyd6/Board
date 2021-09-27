package com.min.board.service;

import com.min.board.domain.Member;
import com.min.board.repository.MemberRepository;

public class MemberService {

    private final MemberRepository MemberRepository;

    public MemberService(MemberRepository MemberRepository) {
        this.MemberRepository = MemberRepository;
    }

    public String join(Member member){
        MemberRepository.save(member);

        return member.getName(); //test
    }

}
