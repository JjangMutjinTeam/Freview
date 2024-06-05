package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.member.AgeGroup;
import com.nuguna.freview.exception.UnsupportedAgeGroupException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustAgeGroupDAO {

  public void updateAgeGroup(int memberSeq, String toAgeGroup) throws UnsupportedAgeGroupException {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      AgeGroup.from(toAgeGroup);
    } catch (UnsupportedAgeGroupException e) {
      throw e;
    }

    String sql = "UPDATE MEMBER "
        + "SET age_group = ? "
        + "WHERE member_seq = ?";
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, toAgeGroup);
      pstmt.setInt(2, memberSeq);
      int rowCount = pstmt.executeUpdate();
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 닉네임 변경 도중 예외 발생", e);
    } finally {
      closeResource(pstmt, conn);
    }
  }

}
