package com.nuguna.freview.dao.member;

import static com.nuguna.freview.config.DbConfig.DB_PW;
import static com.nuguna.freview.config.DbConfig.DB_URL;
import static com.nuguna.freview.config.DbConfig.DB_USER;
import static com.nuguna.freview.config.DbConfig.DRIVER_NAME;
import static com.nuguna.freview.util.DbUtil.closeResource;

import com.nuguna.freview.dto.memberRecommendationInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberDAO {

  private static final String SELECT_MEMBER_INFO =
      "SELECT " +
          "    m.mid, " +
          "    m.nickname, " +
          "    m.profile_photo_url, " +
          "    GROUP_CONCAT(DISTINCT ft.name) AS food_types, " +
          "    GROUP_CONCAT(DISTINCT t.name) AS tags " +
          "FROM " +
          "    member m " +
          "LEFT JOIN " +
          "    member_food_type mft ON m.member_seq = mft.member_seq " +
          "LEFT JOIN " +
          "    food_type ft ON mft.food_type_seq = ft.food_type_seq " +
          "LEFT JOIN " +
          "    member_tag mt ON m.member_seq = mt.member_seq " +
          "LEFT JOIN " +
          "    tag t ON mt.tag_seq = t.tag_seq " +
          "WHERE " +
          "    m.gubun = ? " +
          "GROUP BY " +
          "    m.nickname, m.profile_photo_url;";

  public List<memberRecommendationInfo> selectMemberInfo(String gubun) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<memberRecommendationInfo> list = new ArrayList<>();
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_MEMBER_INFO);
      pstmt.setString(1, gubun);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        memberRecommendationInfo boss = new memberRecommendationInfo();
        boss.setMid(rs.getString("mid"));
        boss.setNickname(rs.getString("nickname"));
        boss.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        boss.setFoodTypes(rs.getString("food_types"));
        boss.setTags(rs.getString("tags"));

        list.add(boss);
      }

      return list;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
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
}
