package com.min.board.repository;

import com.min.board.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    public Member findByUsername(String username);

    public boolean existsByUsername(String username);
}
