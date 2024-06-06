package com.nuguna.freview.dao.post;

import static com.nuguna.freview.config.DbConfig.DB_PW;
import static com.nuguna.freview.config.DbConfig.DB_URL;
import static com.nuguna.freview.config.DbConfig.DB_USER;
import static com.nuguna.freview.config.DbConfig.DRIVER_NAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostDAO {

  private final String COUNT_POST = "SELECT COUNT(1) FROM POST WHERE gubun = ?";

  public int countTotalPost(String gubun) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    int countTotalPosts = 0;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(COUNT_POST);
      pstmt.setString(1, gubun);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        countTotalPosts = rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return countTotalPosts;
  }

  private Connection getConnection() {
    Connection conn = null;
    try {
      Class.forName(DRIVER_NAME);
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
