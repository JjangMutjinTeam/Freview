package com.nuguna.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyConnection {

  private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
  private static final String DB_URL = "jdbc:mariadb://localhost:3306/mysql";
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "0000";

  private static Connection conn;
  private static PreparedStatement pstmt;
  private static ResultSet rs;

  public static int connectDB(){

    int result = 0;

    try{
    Class.forName(DB_DRIVER_CLASS);
    conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    System.out.println("connected to database");
    } catch(Exception e){
      e.printStackTrace();
    }

    try {
      String sql = "SELECT * FROM test";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if(rs.next()){
        result = rs.getInt(1);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      try {
        if(rs!=null)rs.close();
        if(pstmt!=null)pstmt.close();
        if(conn!=null)conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }


    return result;
  }

  public static void main(String[] args) {
    int result = connectDB();
    System.out.print(result);
  }

}
