package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustReviewDAO {

  public void updateReviewUrl(int reviewSeq, String toReviewUrl) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "UPDATE review "
        + "SET review.url = ? "
        + ", review.status = 'WRITTEN' "
        + "WHERE review_seq = ?";

    try {
      conn = getConnection();

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, toReviewUrl);
      pstmt.setInt(2, reviewSeq);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 리뷰 URL 변경 도중 예외 발생", e);
    } finally {
      closeResource(pstmt, conn);
    }
  }
}
