package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.member.MemberGubun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

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
    return MemberGubun.from(gubun);
  }

}
