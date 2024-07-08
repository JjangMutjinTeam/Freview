package com.nuguna.freview.dao.member.boss.page;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.boss.brand.BossMyBrandInfoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BossMyBrandInfoDAO {

  public BossMyBrandInfoDto getBossBrandInfo(int memberSeq) {

    Connection conn = null;
    PreparedStatement basicInfoPstmt = null;
    PreparedStatement foodTypeNamePstmt = null;
    PreparedStatement tagNamePstmt = null;
    PreparedStatement storeNamePstmt = null;
    ResultSet rs = null;

    BossMyBrandInfoDto bossMyBrandInfoDto = null;

    // TODO : 한 번의 쿼리에서 모든 데이터를 가져올 수 있다면 가져올 수 있도록, 쿼리 최적화 필요.

    // 자기소개, 프로필 사진 URL, 나를 찜한 사람 수
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
        + "       m.introduce,\n" + "m.store_location, \n"
        + "       m.profile_photo_url\n"
        + "    FROM\n"
        + "        member as m\n"
        + "    WHERE\n"
        + "        m.member_seq = ?) as m;";

    // 스토어명 가져오기 ( 최적화 필요 )
    String storeNameSql = "SELECT store_name FROM "
        + "store_business_info as sbi "
        + "WHERE sbi.business_number "
        + "= (SELECT "
        + "business_number FROM MEMBER as m "
        + "WHERE m.member_seq = ?)";

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

    try {
      conn = getConnection();

      // Fetch basic info
      basicInfoPstmt = conn.prepareStatement(basicInfoSql);
      basicInfoPstmt.setInt(1, memberSeq);
      rs = basicInfoPstmt.executeQuery();

      String introduce = null;
      String profilePhotoUrl = null;
      String storeLoc = null;
      Integer zzimCount = null;

      if (rs.next()) {
        introduce = rs.getString("introduce");
        profilePhotoUrl = rs.getString("profile_photo_url");
        storeLoc = rs.getString("store_location");
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

      // Store Name SQL
      storeNamePstmt = conn.prepareStatement(storeNameSql);
      storeNamePstmt.setInt(1, memberSeq);
      rs = storeNamePstmt.executeQuery();

      String storeName = null;
      if (rs.next()) {
        storeName = rs.getString("store_name");
      }
      closeResource(storeNamePstmt, null, rs);

      // Create DTO
      bossMyBrandInfoDto = new BossMyBrandInfoDto(
          profilePhotoUrl, introduce, storeLoc, zzimCount, storeName, foodTypes, tagInfos);
    } catch (SQLException e) {
      log.error("SQL error", e);
    } finally {
      closeResource(null, conn);
    }
    return bossMyBrandInfoDto;
  }
}
