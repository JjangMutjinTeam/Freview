package com.nuguna.freview.dao.member.common;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.member.MemberGubun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberUtilDAO {

  public MemberGubun selectMemberGubun(int memberSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "SELECT member.gubun "
        + "FROM MEMBER "
        + "WHERE member_seq = ?";

    String gubun = null;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, memberSeq);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        gubun = rs.getString("gubun");
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 소개 변경 도중 예외 발생", e);
    } finally {
      closeResource(rs);
      closeResource(pstmt, conn);
    }
    return gubun == null ? null : MemberGubun.from(gubun);
  }

  public boolean isValidMember(int memberSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "SELECT 1 "
        + "FROM MEMBER "
        + "WHERE member_seq = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, memberSeq);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        return true;
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 소개 변경 도중 예외 발생", e);
    } finally {
      closeResource(rs);
      closeResource(pstmt, conn);
    }
    return false;
  }

  public boolean doesMemberOwnReview(int memberSeq, int reviewSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "SELECT 1 FROM REVIEW "
        + "WHERE review.cust_seq = ? "
        + "AND review.review_seq = ?";

    boolean isMatched = false;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, memberSeq);
      pstmt.setInt(2, reviewSeq);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        isMatched = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 소개 변경 도중 예외 발생", e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
    return isMatched;
  }

}
