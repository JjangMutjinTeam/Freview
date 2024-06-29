package com.nuguna.freview.dao.request;

import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.request.Request;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestDAO {

  private final String INSERT_APPLY = "INSERT INTO request(from_member_seq, to_member_seq, gubun, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

  public boolean insertApply(Request request) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isInsert = false;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(INSERT_APPLY);
      pstmt.setInt(1, request.getFromMemberSeq());
      pstmt.setInt(2, request.getToMemberSeq());
      pstmt.setString(3, request.getGubun());
      pstmt.setString(4, request.getStatus());
      pstmt.setTimestamp(5, request.getCreatedAt());
      pstmt.setTimestamp(6, request.getUpdatedAt());

      int rows = pstmt.executeUpdate();
      if (rows > 0) {
        isInsert = true;
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return isInsert;
  }
}
