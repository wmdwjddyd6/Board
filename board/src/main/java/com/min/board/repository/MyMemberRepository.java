package com.min.board.repository;

import com.min.board.domain.Member;

public class MyMemberRepository implements MemberRepository{

    @Override
    public Member save(Member member) {
        return member;
    }
}
