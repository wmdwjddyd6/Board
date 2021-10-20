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
        String sql = "insert into `board`.`tb_userinfo` (username, userid, password) value (?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserId());
            pstmt.setString(3, member.getPassword());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setUserName(rs.getString(1));
                member.setUserId(rs.getString(2));
                member.setPassword(rs.getString(3));
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
