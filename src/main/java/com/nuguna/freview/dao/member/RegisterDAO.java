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

  public int getDuplicationResultByID(String id) { //id 중복 확인
    int result = 0;
    System.out.println("dao실행전" + result);
    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "SELECT * FROM member WHERE id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result++;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
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
        e.printStackTrace();
      }
    }
    System.out.println("dao실행후" + result);
    return result;
  }

  public int getDuplicationResultByNickName(String nickName) { // 닉네임 중복 확인

    int result = 0;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "SELECT * FROM member WHERE nickname=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, nickName);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = 1;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
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
        e.printStackTrace();
      }
    }

    return result;

  }

  public int insertReviewer(String id, String password, String email, String nickname, String agegroup) { // 체험단 회원가입

    int result = 0;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      String sql = "INSERT INTO member (gubun, id, pw, nickname, email, age_group, introduce, business_number, store_location, profile_photo_url)\n"
          + "VALUES('C', ?, ?, ?, ?, ?, null, null, null, 'http://example.com/photo57.jpg')";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      pstmt.setString(2, password);
      pstmt.setString(3, nickname);
      pstmt.setString(4, email);
      pstmt.setString(5, agegroup);
      result = pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
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
        e.printStackTrace();
      }
    }

    return result;
  }

  public int getCheckCollectBuisnessInfo(String businessInfo) { // 사업자등록번호 확인하기
    int result = 0;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "SELECT * FROM store_business_info WHERE business_number=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, businessInfo);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = 1;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
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
        e.printStackTrace();
      }
    }

    return result;
  }

  public int insertBoss(String id, String password,String nickname ,String email, String buisnessNumber, String agegroup, String storeLoc) { // 사장님 회원가입
    int result = 0;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "INSERT INTO member (gubun, id, pw, nickname, email, age_group, introduce, business_number, store_location, profile_photo_url)\n"
          + "VALUES('B', ?, ?, ?, ?, ?, null, ?, ?, 'http://example.com/photo2.jpg')"; // Boss 회원 가입

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      pstmt.setString(2, password);
      pstmt.setString(3, nickname);
      pstmt.setString(4, email);
      pstmt.setString(5, agegroup);
      pstmt.setString(6, buisnessNumber);
      pstmt.setString(7, storeLoc);

      result = pstmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
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
        e.printStackTrace();
      }
    }
    return result;
  }

  public int getCheckDuplicatedInMember(String buisnessInfo) {
    int result = 0;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "SELECT * FROM member WHERE business_number=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, buisnessInfo);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = 1;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
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
        e.printStackTrace();
      }
    }

    return result;
  }

  public String getFindIdByEmail(String email) {
    String id = null;

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      String sql = "SELECT id\n"
          + "FROM member\n"
          + "WHERE email = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,email);
      rs = pstmt.executeQuery();
      if(rs.next()){
        id = rs.getString(1) ;
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

    return id;
  }
}
