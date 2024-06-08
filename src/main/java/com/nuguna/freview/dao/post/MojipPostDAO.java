package com.nuguna.freview.dao.post;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.MojipPostDTO;
import com.nuguna.freview.entity.post.PostGubun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MojipPostDAO {

  //TODO: 성능 개선
  private final String SELECT_MOJIP = "SELECT " +
      "    p.post_seq, " +
      "    m.member_seq, " +
      "    m.gubun, " +
      "    m.business_number, " +
      "    m.profile_photo_url, " +
      "    GROUP_CONCAT(DISTINCT ft.name ORDER BY ft.name ASC SEPARATOR ', ') AS food_type_names, " +
      "    GROUP_CONCAT(DISTINCT t.name ORDER BY t.name ASC SEPARATOR ', ') AS tag_names, " +
      "    s.store_name, " +
      "    p.title, " +
      "    p.apply_start_date, " +
      "    p.apply_end_date, " +
      "    p.experience_date, " +
      "    p.content, " +
      "    (SELECT COUNT(*) FROM ddabong d WHERE d.post_seq = p.post_seq) AS number_of_ddabong " +
      "FROM " +
      "    post p " +
      "LEFT JOIN " +
      "    member m ON p.member_seq = m.member_seq " +
      "LEFT JOIN " +
      "    member_food_type mft ON m.member_seq = mft.member_seq " +
      "LEFT JOIN " +
      "    food_type ft ON mft.food_type_seq = ft.food_type_seq " +
      "LEFT JOIN " +
      "    member_tag mt ON m.member_seq = mt.member_seq " +
      "LEFT JOIN " +
      "    tag t ON mt.tag_seq = t.tag_seq " +
      "LEFT JOIN " +
      "    store_business_info s ON m.business_number = s.business_number " +
      "WHERE" +
      "     p.gubun = ? " +
      "GROUP BY " +
      "    p.post_seq, m.member_seq, m.gubun, m.business_number, m.profile_photo_url, s.store_name, p.title, p.apply_start_date, p.apply_end_date, p.experience_date, p.content";

  public List<MojipPostDTO> getMojipPostList() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<MojipPostDTO> mojipPostList = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_MOJIP);
      pstmt.setString(1, PostGubun.MJ.getCode());
      rs = pstmt.executeQuery();

      while (rs.next()) {
        MojipPostDTO dto = new MojipPostDTO();
        dto.setPostSeq(rs.getInt("post_seq"));
        dto.setMemberSeq(rs.getInt("member_seq"));
        dto.setBusinessNumber(rs.getString("business_number"));
        dto.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        dto.setCodeName(rs.getString("food_type_names"));
        dto.setName(rs.getString("tag_names"));
        dto.setStoreName(rs.getString("store_name"));
        dto.setTitle(rs.getString("title"));
        dto.setApplyStartDate(rs.getDate("apply_start_date"));
        dto.setApplyEndDate(rs.getDate("apply_end_date"));
        dto.setExperienceDate(rs.getDate("experience_date"));
        dto.setContent(rs.getString("content"));
        dto.setNumberOfDdabong(rs.getInt("number_of_ddabong"));

        mojipPostList.add(dto);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return mojipPostList;
  }

}
