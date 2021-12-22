package com.min.board.repository.jpa;

import com.min.board.model.Member;
//import com.min.board.model.MemberID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    boolean existsByUsername(String username);
}
