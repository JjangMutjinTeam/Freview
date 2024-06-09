package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustLovecallDAO {

  public void submitLovecall(int bossSeq, int custSeq, String benefitDetails) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "INSERT INTO request (from_member_seq, to_member_seq, "
        + "come_date, come_or_not, review_or_not, "
        + "gubun, from_post, benefit_detail, status) "
        + "VALUES (?, ?, NULL, FALSE, FALSE, 'LC', NULL, ?, 'PENDING')";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      pstmt.setInt(2, custSeq);
      pstmt.setString(3, benefitDetails);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 러브콜 입력 도중 예외 발생", e);
    } finally {
      closeResource(pstmt, conn);
    }
  }
}
