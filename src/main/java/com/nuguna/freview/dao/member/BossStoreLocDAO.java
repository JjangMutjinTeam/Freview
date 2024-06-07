package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BossStoreLocDAO {

  public void updateStoreLoc(int memberSeq, String updateStoreLoc ){
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = " UPDATE member "
        + " SET store_loc = ? "
        + " WHERE member_seq = ? ";
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, updateStoreLoc);
      pstmt.setInt(2, memberSeq);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 주소 변경 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }

  }

}
