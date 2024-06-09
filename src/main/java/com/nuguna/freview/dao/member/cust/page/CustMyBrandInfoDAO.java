package com.nuguna.freview.dao.member.cust.page;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.cust.brand.CustMyBrandInfoDto;
import com.nuguna.freview.dto.cust.brand.CustMyBrandInfoDto.ReviewLogInfoDto;
import com.nuguna.freview.entity.review.ReviewStatus;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustMyBrandInfoDAO {

  public CustMyBrandInfoDto getCustBrandInfo(int memberSeq) {

    Connection conn = null;
    PreparedStatement basicInfoPstmt = null;
    PreparedStatement foodTypeNamePstmt = null;
    PreparedStatement tagNamePstmt = null;
    PreparedStatement reviewLogPstmt = null;
    ResultSet rs = null;

    CustMyBrandInfoDto custMyBrandInfoDto = null;

    // TODO : 한 번의 쿼리에서 모든 데이터를 가져올 수 있다면 가져올 수 있도록, 쿼리 최적화 필요.

    // 자기소개, 연령대, 닉네임, 프로필 사진 URL, 나를 찜한 사람 수
    String basicInfoSql = "SELECT\n"
        + "    m.*,\n"
        + "    (SELECT\n"
        + "         COUNT(*)\n"
        + "     FROM zzim as z\n"
        + "     WHERE\n"
        + "          z.to_member_seq = ms) as zzimCount\n"
        + "FROM\n"
        + "    (SELECT\n"
        + "       m.member_seq as ms,\n"
        + "       m.introduce,\n"
        + "       m.age_group,\n"
        + "       m.nickname,\n"
        + "       m.profile_photo_url\n"
        + "    FROM\n"
        + "        member as m\n"
        + "    WHERE\n"
        + "        m.member_seq = ?) as m;";

    // 활동분야 이름들 가져오기 SQL
    String foodTypeNameSql = "SELECT\n"
        + "    ft.name as name \n"
        + "FROM\n"
        + "    food_type ft\n"
        + "WHERE\n"
        + "    ft.food_type_seq IN (\n"
        + "            SELECT\n"
        + "                mft.food_type_seq\n"
        + "            FROM\n"
        + "                member_food_type mft\n"
        + "            WHERE\n"
        + "                mft.member_seq = ?\n"
        + "        );";

    // 나의 태그 가져오기 SQL
    String tagNameSql = "SElECT\n"
        + "    t.name\n"
        + "FROM\n"
        + "    tag t\n"
        + "WHERE\n"
        + "    t.tag_seq IN (\n"
        + "            SELECT\n"
        + "                mt.tag_seq\n"
        + "            FROM\n"
        + "                member_tag mt\n"
        + "            WHERE\n"
        + "                mt.member_seq = ?\n"
        + "        );";

    String reviewLogSql = "SELECT\n"
        + "    rrr.status as status,\n"
        + "    sbi.store_name as store_name,\n"
        + "    rrr.visited_date as visited_date,\n"
        + "    rrr.url as url\n"
        + "FROM\n"
        + "    (SELECT\n"
        + "        rr.status,\n"
        + "        m.business_number,\n"
        + "        rr.visited_date,\n"
        + "        rr.url\n"
        + "    FROM\n"
        + "        (SELECT\n"
        + "            r.status,\n"
        + "            r.boss_seq,\n"
        + "            r.visited_date,\n"
        + "            r.url\n"
        + "        FROM\n"
        + "            review r\n"
        + "        WHERE\n"
        + "            r.cust_seq = ?) rr\n"
        + "    INNER JOIN\n"
        + "        member m\n"
        + "    ON\n"
        + "        rr.boss_seq = m.member_seq) rrr\n"
        + "    INNER JOIN\n"
        + "        store_business_info sbi\n"
        + "    ON\n"
        + "        rrr.business_number = sbi.business_number";

    try {
      conn = getConnection();

      // Fetch basic info
      basicInfoPstmt = conn.prepareStatement(basicInfoSql);
      basicInfoPstmt.setInt(1, memberSeq);
      rs = basicInfoPstmt.executeQuery();

      String introduce = null;
      String ageGroup = null;
      String nickname = null;
      String profilePhotoUrl = null;
      Integer zzimCount = null;

      if (rs.next()) {
        introduce = rs.getString("introduce");
        ageGroup = rs.getString("age_group");
        nickname = rs.getString("nickname");
        profilePhotoUrl = rs.getString("profile_photo_url");
        zzimCount = rs.getInt("zzimCount");
      }

      closeResource(basicInfoPstmt, null, rs);

      // Fetch food types
      foodTypeNamePstmt = conn.prepareStatement(foodTypeNameSql);
      foodTypeNamePstmt.setInt(1, memberSeq);
      rs = foodTypeNamePstmt.executeQuery();

      List<String> foodTypes = new ArrayList<>();
      while (rs.next()) {
        foodTypes.add(rs.getString("name"));
      }

      closeResource(foodTypeNamePstmt, null, rs);

      // Fetch tags
      tagNamePstmt = conn.prepareStatement(tagNameSql);
      tagNamePstmt.setInt(1, memberSeq);
      rs = tagNamePstmt.executeQuery();

      List<String> tagInfos = new ArrayList<>();
      while (rs.next()) {
        tagInfos.add(rs.getString("name"));
      }

      closeResource(tagNamePstmt, null, rs);

      // Fetch review logs
      reviewLogPstmt = conn.prepareStatement(reviewLogSql);
      reviewLogPstmt.setInt(1, memberSeq);
      rs = reviewLogPstmt.executeQuery();

      List<ReviewLogInfoDto> reviewInfos = new ArrayList<>();
      while (rs.next()) {
        String reviewStatus = ReviewStatus.from(rs.getString("status")).getStatus();
        String storeName = rs.getString("store_name");
        Date visitedDate = rs.getDate("visited_date");
        String reviewUrl = rs.getString("url");

        reviewInfos.add(new ReviewLogInfoDto(reviewStatus, storeName, visitedDate, reviewUrl));
      }

      closeResource(reviewLogPstmt, null, rs);

      // Create DTO
      custMyBrandInfoDto = new CustMyBrandInfoDto(
          profilePhotoUrl, nickname, ageGroup, introduce, zzimCount, foodTypes, tagInfos,
          reviewInfos);

    } catch (SQLException e) {
      log.error("SQL error", e);
    } finally {
      closeResource(null, conn);
    }
    return custMyBrandInfoDto;
  }
}