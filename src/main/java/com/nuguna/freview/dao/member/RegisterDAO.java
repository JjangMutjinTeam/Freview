package com.nuguna.freview.dao.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO {

  private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
  private static final String DB_URL = "jdbc:mariadb://localhost:3306/freview";
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "0000";

  private static Connection conn;
  private static PreparedStatement pstmt;
  private static ResultSet rs;

  public int getDuplicationResultByID(String id) {
    int result = 0;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "SELECT * FROM member WHERE mid=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,id);
      rs = pstmt.executeQuery();
      if(rs.next()){
        result = 1;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      try {
        if(rs!=null)rs.close();
        if(pstmt!=null)pstmt.close();
        if(conn!=null)conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return result;
  }
}
