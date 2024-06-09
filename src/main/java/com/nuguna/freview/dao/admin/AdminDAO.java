package com.nuguna.freview.dao.admin;

import static com.nuguna.freview.config.DbConfig.*;

import com.nuguna.freview.dto.StoreAndBoss;
import com.nuguna.freview.entity.admin.StoreBusinessInfo;
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

  private final String SELECT_ALL_MEMBER_NOT_ADMIN = "SELECT * FROM member WHERE gubun != 'A'";
  private final String SELECT_MEMBER_BY_ID = "SELECT * FROM member WHERE id = ?";
  private final String DELETE_MEMBER_BY_ID = "DELETE FROM member WHERE mid = ?";
  private final String SELECT_ADMIN_PW = "SELECT MPW FROM member WHERE gubun = 'A'";
  private final String SELECT_STORE_BUSINESS_INFO = "SELECT s.store_name, s.business_number, m.mid, m.created_at FROM store_business_info s LEFT JOIN member m ON s.business_number = m.business_number";
  private final String DELETE_STORE_BY_BUSINESS_NUMBER = "DELETE FROM store_business_info WHERE business_number = ?";
  private final String INSERT_STORE = "INSERT INTO store_business_info(business_number, store_name) VALUES(?, ?)";

  public List<Member> selectAllMember() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Member> list = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_ALL_MEMBER_NOT_ADMIN);
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

  public boolean deleteMember(String userId) {
    boolean isDeleted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(DELETE_MEMBER_BY_ID);
      pstmt.setString(1, userId);

      int rows = pstmt.executeUpdate();
      if (rows > 0) {
        isDeleted = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }

    return isDeleted;
  }

  public String selectAdminPW() {
    String adminPW = "";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_ADMIN_PW);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        adminPW = rs.getString(1);
      }

      return adminPW;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
  }

  public List<StoreAndBoss> selectStoreBusinessInfo() {
    List<StoreAndBoss> list = new ArrayList<>();

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_STORE_BUSINESS_INFO);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        StoreAndBoss storeAndBoss = new StoreAndBoss();
        storeAndBoss.setStoreName(rs.getString(1));
        storeAndBoss.setBusinessNumber(rs.getString(2));
        storeAndBoss.setMid(rs.getString(3));
        storeAndBoss.setCreatedAt(rs.getTimestamp(4));

        list.add(storeAndBoss);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return list;
  }

  public boolean deleteStore(String businessNumber) {
    boolean isDeleted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(DELETE_STORE_BY_BUSINESS_NUMBER);
      pstmt.setString(1, businessNumber);

      int rows = pstmt.executeUpdate();
      if (rows > 0) {
        isDeleted = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }

    return isDeleted;
  }

  public boolean insertStore(StoreBusinessInfo storeBusinessInfo) {
    boolean isInserted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(INSERT_STORE);
      pstmt.setString(1, storeBusinessInfo.getBusinessNumber());
      pstmt.setString(2, storeBusinessInfo.getStoreName());

      int rows = pstmt.executeUpdate();
      if (rows > 0) {
        isInserted = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }

    return isInserted;
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
