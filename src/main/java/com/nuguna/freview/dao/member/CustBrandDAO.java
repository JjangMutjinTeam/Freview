package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustBrandDAO {

  public boolean checkNicknameIsExist(String toNickname) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      String sql = "SELECT member_seq "
          + "FROM MEMBER "
          + "WHERE NICKNAME = ?";

      boolean checkResult = false;
      try {
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, toNickname);
        rs = pstmt.executeQuery();

        if(rs.next()){
          checkResult = true;
        }
      } catch (SQLException e) {
        throw new RuntimeException("SQLException : 닉네임 체크 도중 예외 발생",e);
      } finally {
        closeResource(pstmt, conn, rs);
      }
    return checkResult;
  }

  public void updateNickname(int memberSeq, String toNickname) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "UPDATE MEMBER "
        + "SET nickname = ? "
        + "WHERE member_seq = ?";
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, toNickname);
      pstmt.setInt(2, memberSeq);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 닉네임 변경 도중 예외 발생" , e);
    } finally {
      closeResource(pstmt, conn);
    }
  }
}
