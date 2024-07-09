package com.nuguna.freview.dao.member;

import com.nuguna.freview.util.DbUtil;
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
    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn, rs);
    }
    System.out.println("dao실행후" + result);
    return result;
  }

  public int getDuplicationResultByNickName(String nickName) { // 닉네임 중복 확인

    int result = 0;

    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn, rs);
    }

    return result;

  }

  public int insertReviewer(String id, String password, String email, String nickname, String agegroup) { // 체험단 회원가입

    int result = 0;

    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn, rs);
    }

    return result;
  }

  public int getCheckCollectBuisnessInfo(String businessInfo) { // 사업자등록번호 확인하기
    int result = 0;

    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn, rs);
    }

    return result;
  }

  public int insertBoss(String id, String password,String nickname ,String email, String buisnessNumber, String agegroup, String storeLoc) { // 사장님 회원가입
    int result = 0;

    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn);
    }
    return result;
  }

  public int getCheckDuplicatedInMember(String buisnessInfo) {
    int result = 0;

    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn, rs);
    }

    return result;
  }

  public String getFindIdByEmail(String email) {
    String id = null;

    conn = DbUtil.getConnection();

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
      DbUtil.closeResource(pstmt, conn, rs);
    }

    return id;
  }

  public int getCheckMemberByEmailAndID(String email, String id) {

    int num = 0;

    conn = DbUtil.getConnection();

    try {
      String sql = "SELECT *\n"
          + "FROM member\n"
          + "WHERE email = ? AND"
          +" id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,email);
      pstmt.setString(2,id);
      rs = pstmt.executeQuery();
      if(rs.next()){
        num = 1 ;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      DbUtil.closeResource(pstmt, conn, rs);
    }

    return num;
  }

  public void updateUserPW(String shaPw, String userID) {

    conn = DbUtil.getConnection();

    try {
      String sql = "UPDATE member SET pw = ? WHERE id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,shaPw);
      pstmt.setString(2,userID);
      pstmt.executeUpdate();
      System.out.println("비밀번호 업데이트 성공");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      DbUtil.closeResource(pstmt,conn);
    }

  }
}
