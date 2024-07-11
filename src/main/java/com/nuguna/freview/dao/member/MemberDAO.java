package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

  //TODO: member_seq에 대한 외래키 제약을 없애서 member는 탈퇴되어도 작성한 데이터는 남아있게끔 설계해야 함
  public boolean deleteMember(int memberSeq) {
    String deleteMemberSql = "DELETE FROM member WHERE member_seq = ?";

    boolean isDeleted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();

      pstmt = conn.prepareStatement(deleteMemberSql);
      pstmt.setInt(1, memberSeq);

      int rows = pstmt.executeUpdate();
      if (rows > 0) {
        isDeleted = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }

    return isDeleted;
  }

  public boolean isMatchingMember(int memberSeq, String pw) {

    String sql = "select count(*) from member where member_seq = ? and pw = ?";

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

  public int updateEmail(int memberSeq, String email) {
    String sql = "update member set email = ? where member_seq = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, email);
      pstmt.setInt(2, memberSeq);

      int result = pstmt.executeUpdate();
      return result;
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 이메일을 업데이트 하는 도중 에러 발생", e);
    } finally {
      closeResource(pstmt, conn);
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

      return pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 비밀번호를 업데이트 하는 도중 에러 발생", e);
    } finally {
      closeResource(pstmt, conn);
    }
  }
}
