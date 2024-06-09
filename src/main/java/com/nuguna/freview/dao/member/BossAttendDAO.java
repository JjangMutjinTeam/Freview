package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BossAttendDAO {
  public boolean bossAttend(int postSeq) {
    Connection conn = getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isInserted = false;

    String sql = "UPDATE request SET status = ? WHERE from_post = ?";

    try (PreparedStatement statement = conn.prepareStatement(sql)) {
      // 매개변수 설정
      statement.setString(1, "success");
      statement.setInt(2, postSeq);

      // 쿼리 실행
      int rowsUpdated = statement.executeUpdate();

      if (rowsUpdated > 0) {
        isInserted = true;
      }

    } catch(SQLException e) {
      throw new RuntimeException("SQLException: 참석 여부 체크 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return isInserted;
  }
}
