package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.MemberRecommendationInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationMemberDAO {

  public List<MemberRecommendationInfo> selectMemberByCursorPaging(String gubun, int previousPostSeq, int limit) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<MemberRecommendationInfo> list = new ArrayList<>();

    String query = "SELECT " +
        "    m.member_seq, " +
        "    m.id, " +
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
        "    m.gubun = ? AND m.member_seq < ? " +
        "GROUP BY " +
        "    m.member_seq, m.id, m.nickname, m.profile_photo_url " +
        "ORDER BY " +
        "    m.member_seq DESC " +
        "LIMIT ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, gubun);
      pstmt.setInt(2, previousPostSeq);
      pstmt.setInt(3, limit);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        MemberRecommendationInfo member = new MemberRecommendationInfo();
        member.setMemberSeq(rs.getInt("member_seq"));
        member.setId(rs.getString("id"));
        member.setNickname(rs.getString("nickname"));
        member.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        member.setFoodTypes(rs.getString("food_types"));
        member.setTags(rs.getString("tags"));

        list.add(member);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return list;
  }

  public List<MemberRecommendationInfo> filterMembers(String gubun, int previousMemberSeq, int limit, String[] foodTypes, String[] tags) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<MemberRecommendationInfo> members = new ArrayList<>();
    StringBuilder sql = getMemberRecommendationFilteringQuery(foodTypes, tags);

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql.toString());

      int paramIndex = 1;
      pstmt.setString(paramIndex++, gubun);
      pstmt.setInt(paramIndex++, previousMemberSeq);

      if (foodTypes != null) {
        for (String foodType : foodTypes) {
          pstmt.setString(paramIndex++, foodType);
        }
      }

      if (tags != null) {
        for (String tag : tags) {
          pstmt.setString(paramIndex++, tag);
        }
      }

      pstmt.setInt(paramIndex, limit);

      rs = pstmt.executeQuery();
      while (rs.next()) {
        MemberRecommendationInfo member = new MemberRecommendationInfo();
        member.setMemberSeq(rs.getInt("member_seq"));
        member.setId(rs.getString("id"));
        member.setNickname(rs.getString("nickname"));
        member.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        member.setFoodTypes(rs.getString("food_types"));
        member.setTags(rs.getString("tags"));

        members.add(member);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
    return members;
  }

  private StringBuilder getMemberRecommendationFilteringQuery(String[] foodTypes, String[] tags) {
    StringBuilder sql = new StringBuilder("SELECT " +
        "    m.member_seq," +
        "    m.id, " +
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
        "    m.gubun = ? AND m.member_seq < ? "
    );

    if (foodTypes != null && foodTypes.length > 0) {
      sql.append(" AND ft.name IN (");
      for (int i = 0; i < foodTypes.length; i++) {
        sql.append("?");
        if (i < foodTypes.length - 1) sql.append(", ");
      }
      sql.append(")");
    }

    if (tags != null && tags.length > 0) {
      sql.append(" AND t.name IN (");
      for (int i = 0; i < tags.length; i++) {
        sql.append("?");
        if (i < tags.length - 1) sql.append(", ");
      }
      sql.append(")");
    }

    sql.append(" GROUP BY m.member_seq, m.id, m.nickname, m.profile_photo_url " +
        "ORDER BY m.member_seq DESC " +
        "LIMIT ?");

    return sql;
  }
}
