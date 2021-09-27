package com.min.board.repository;

import com.min.board.domain.Member;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;

public class MyMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public MyMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
//        String sql = "insert into member (name) value (?)";
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            conn = getConnection();
//            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            pstmt.setString(1, member.getName());
//
//            pstmt.executeUpdate();
//            rs = pstmt.getGeneratedKeys();
//
//            if (rs.next()) {
//                member.setId(rs.getLong(1));
//            } else {
//                throw new SQLException("id 조회 실패");
//            }
//            return member;
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        } finally {
//            close(conn, pstmt, rs);
//        }
        return member;
    }

}
