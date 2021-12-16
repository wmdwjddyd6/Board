package com.min.board.repository;

import com.min.board.model.Member;

// 저장소 인터페이스 (DB에 저장, 조회, 삭제, 업데이트 등 기능)
public interface MemberRepository {

    Member save(Member memer);
}
