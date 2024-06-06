package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustIntroduceDAO {

  public void updateIntroduce(int memberSeq, String toIntroduce) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "UPDATE MEMBER "
        + "SET introduce = ? "
        + "WHERE member_seq = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, toIntroduce);
      pstmt.setInt(2, memberSeq);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 소개 변경 도중 예외 발생", e);
    } finally {
      closeResource(pstmt, conn);
    }
  }
}
