package com.nuguna.freview.dao.admin;

import com.nuguna.freview.entity.member.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminDAO {

  static final String DB_URL = "jdbc:mariadb://localhost:3306/freview";
  static final String DB_USER = "root";
  static final String DB_PW = "0000";
  static final String driver = "org.mariadb.jdbc.Driver";

  private final String SELECT_ALL_MEMBER = "SELECT * FROM member";
  private final String SELECT_MEMBER_BY_ID = "SELECT * FROM member WHERE id = ?";
  private final String DELETE_MEMBER_BY_ID = "DELETE FROM member WHERE id = ?";

  public List<Member> selectAllMember() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Member> list = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_ALL_MEMBER);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Member member = new Member();
        member.setMemberSeq(rs.getInt(1));
        member.setGubun(rs.getString(2));
        member.setMid(rs.getString(3));
        member.setMpw(rs.getString(4));
        member.setNickname(rs.getString(5));
        member.setEmail(rs.getString(6));
        member.setAgeGroup(rs.getString(7));
        member.setIntroduce(rs.getString(8));
        member.setBusinessNumber(rs.getString(9));
        member.setStoreLoc(rs.getString(10));
        member.setProfilePhotoUrl(rs.getString(11));
        member.setCreatedAt(rs.getTimestamp(12));
        member.setUpdatedAt(rs.getTimestamp(13));

        list.add(member);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return list;
  }

  private Connection getConnection() {
    Connection conn = null;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    } catch (ClassNotFoundException e) {
      log.error("JDBC Driver not found");
    } catch (SQLException e) {
      log.error("connection failed");
    }
    return conn;
  }

  public void closeResource(PreparedStatement pstmt, Connection conn, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void closeResource(PreparedStatement pstmt, Connection conn) {
    try {
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
