package com.nuguna.freview.dao.member;

import com.nuguna.freview.entity.member.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

  private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
  private static final String DB_URL = "jdbc:mariadb://localhost:3306/freview";
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "0000";

  private static Connection conn;
  private static PreparedStatement pstmt;
  private static ResultSet rs;

  public Member getMemberByIdPw(String id, String password) {
    Member member = null;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      String sql = "SELECT * FROM member WHERE id=? AND pw=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      pstmt.setString(2, password);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        member = new Member();

        int memberSeq = rs.getInt("member_seq");
        member.setMemberSeq(memberSeq);
        String nickname = rs.getString("nickname");
        member.setNickname(nickname);
        String gubun = rs.getString("gubun");
        member.setGubun(gubun);
        String photo = rs.getString("profile_photo_url");
        member.setProfilePhotoUrl(photo);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return member;
  }
}
