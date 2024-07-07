package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

  public boolean isMatchingMember(int memberSeq, String pw) {

    String sql = "select count(*) from member where member_seq = ? and pw = ?";

    //TODO: 암호 암호화 메서드 활용
//    String encryptedPw =
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, memberSeq);
      pstmt.setString(2, pw);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        int count = rs.getInt(1);
        return count > 0;
      }
      return false;

    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 멤버 매칭하는 도중 에러 발생", e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
  }

  public int updatePassword(int memberSeq, String newPw) {
    //TODO: 암호화 필요

    String sql = "UPDATE member SET pw = ? WHERE member_seq = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, newPw);
      pstmt.setInt(2, memberSeq);

      int updateRows = pstmt.executeUpdate();

      return updateRows;
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 비밀번호를 업데이트 하는 도중 에러 발생", e);
    } finally {
      closeResource(pstmt, conn);
    }
  }

}
