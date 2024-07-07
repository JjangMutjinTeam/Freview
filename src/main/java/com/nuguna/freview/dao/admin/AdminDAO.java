package com.nuguna.freview.dao.admin;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.StoreAndBoss;
import com.nuguna.freview.entity.admin.StoreBusinessInfo;
import com.nuguna.freview.entity.member.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminDAO {

  private final String DELETE_MEMBER_BY_ID = "DELETE FROM member WHERE id = ?";
  private final String SELECT_STORE_BUSINESS_INFO = "SELECT s.store_name, s.business_number, m.id, m.created_at FROM store_business_info s LEFT JOIN member m ON s.business_number = m.business_number";
  private final String DELETE_STORE_BY_BUSINESS_NUMBER = "DELETE FROM store_business_info WHERE business_number = ?";
  private final String INSERT_STORE = "INSERT INTO store_business_info(business_number, store_name) VALUES(?, ?)";

  public boolean getMatchingMember(String memberPw) {
    //TODO: 암호 암호화 메서드 활용
//    String encryptedPw =
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "select count(*) from member where gubun = 'A' and pw = ?";
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, memberPw);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        int count = rs.getInt(1);
        return count > 0;
      }
      return false;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
  }

  public List<Member> getMemberList(int previousMemberSeq, int LIMIT) {
    String sql = "SELECT * FROM member WHERE gubun != 'A' AND member_seq < ? ORDER BY member_seq DESC LIMIT ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Member> list = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, previousMemberSeq);
      pstmt.setInt(2, LIMIT);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Member member = new Member();
        member.setMemberSeq(rs.getInt(1));
        member.setGubun(rs.getString(2));
        member.setId(rs.getString(3));
        member.setPw(rs.getString(4));
        member.setNickname(rs.getString(5));
        member.setEmail(rs.getString(6));
        member.setAgeGroup(rs.getString(7));
        member.setIntroduce(rs.getString(8));
        member.setBusinessNumber(rs.getString(9));
        member.setStoreLocation(rs.getString(10));
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

  public List<Member> getMemberList(int previousMemberSeq, int LIMIT, String searchWord) {
    String sql = "SELECT * FROM member WHERE gubun != 'A' AND member_seq < ? " +
        "AND (id LIKE CONCAT('%', ?, '%') OR nickname LIKE CONCAT('%', ?, '%')) " +
        "ORDER BY member_seq DESC LIMIT ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Member> list = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      int paramIndex = 1;
      pstmt.setInt(paramIndex++, previousMemberSeq);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setInt(paramIndex, LIMIT);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        Member member = new Member();
        member.setMemberSeq(rs.getInt(1));
        member.setGubun(rs.getString(2));
        member.setId(rs.getString(3));
        member.setPw(rs.getString(4));
        member.setNickname(rs.getString(5));
        member.setEmail(rs.getString(6));
        member.setAgeGroup(rs.getString(7));
        member.setIntroduce(rs.getString(8));
        member.setBusinessNumber(rs.getString(9));
        member.setStoreLocation(rs.getString(10));
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

  public boolean deleteMember(String memberId) {
    boolean isDeleted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(DELETE_MEMBER_BY_ID);
      pstmt.setString(1, memberId);

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

  public List<StoreAndBoss> getStoreBusinessInfo() {
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

}
