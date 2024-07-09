package com.nuguna.freview.dao.zzim;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZzimDAO {

  public void toggleZzim(int fromMemberSeq, int toMemberSeq) {
    Connection conn = null;
    PreparedStatement selectPstmt = null;
    PreparedStatement insertPstmt = null;
    PreparedStatement deletePstmt = null;
    ResultSet rs = null;

    String selectSql = "SELECT 1 FROM zzim "
        + "WHERE from_member_seq = ? "
        + "AND to_member_seq = ?";

    String insertSql = "INSERT INTO zzim(from_member_seq, to_member_seq) "
        + "VALUES(?,?)";

    String deleteSql = "DELETE FROM zzim "
        + "WHERE from_member_seq = ? "
        + "AND to_member_seq = ?";

    try {
      conn = getConnection();

      conn.setAutoCommit(false);

      selectPstmt = conn.prepareStatement(selectSql);
      selectPstmt.setInt(1, fromMemberSeq);
      selectPstmt.setInt(2, toMemberSeq);

      rs = selectPstmt.executeQuery();

      if (rs.next()) { // 찜이 있으면 삭제
        deletePstmt = conn.prepareStatement(deleteSql);
        deletePstmt.setInt(1, fromMemberSeq);
        deletePstmt.setInt(2, toMemberSeq);
        deletePstmt.executeUpdate();
      } else { // 찜이 없으면 추가
        insertPstmt = conn.prepareStatement(insertSql);
        insertPstmt.setInt(1, fromMemberSeq);
        insertPstmt.setInt(2, toMemberSeq);
        insertPstmt.executeUpdate();
      }
      conn.commit();
    } catch (SQLException e) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException rollbackEx) {
          throw new RuntimeException("예외가 발생했으나 롤백에 실패함");
        }
      }
      throw new RuntimeException("SQLException : 찜 변경 도중 예외 발생", e);
    } finally {
      closeResource(rs);
      closeResource(deletePstmt);
      closeResource(selectPstmt);
      closeResource(insertPstmt, conn);
    }
  }

}
