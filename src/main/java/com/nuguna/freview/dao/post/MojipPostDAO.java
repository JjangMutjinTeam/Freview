package com.nuguna.freview.dao.post;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.MojipPostDTO;
import com.nuguna.freview.entity.post.Post;
import com.nuguna.freview.entity.post.PostGubun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MojipPostDAO {

  //TODO: 성능 개선
  private final String SELECT_MOJIP_LIST = "SELECT " +
      "    p.post_seq, " +
      "    m.member_seq, " +
      "    m.gubun, " +
      "    m.business_number, " +
      "    m.profile_photo_url, " +
      "    GROUP_CONCAT(DISTINCT ft.name ORDER BY ft.name ASC SEPARATOR ', ') AS food_type_names, "
      +
      "    GROUP_CONCAT(DISTINCT t.name ORDER BY t.name ASC SEPARATOR ', ') AS tag_names, " +
      "    s.store_name, " +
      "    p.title, " +
      "    p.apply_start_date, " +
      "    p.apply_end_date, " +
      "    p.experience_date, " +
      "    p.content, " +
      "    (SELECT COUNT(*) FROM Likes l WHERE l.post_seq = p.post_seq) AS number_of_likes" +
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

  private final String INSERT_MOJIP = "INSERT INTO post(title, member_seq, apply_start_date, apply_end_date, experience_date, content, gubun) VALUES(?, ?, ?, ?, ?, ?, ?) ";

  private final String SELECT_MOJIP_BY_SEQ = "SELECT " +
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
      "    (SELECT COUNT(*) FROM Likes l WHERE l.post_seq = p.post_seq) AS number_of_likes " +
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
      "WHERE " +
      "     p.gubun = ? AND " +
      "     p.post_seq = ? " +
      "GROUP BY " +
      "    p.post_seq, m.member_seq, m.gubun, m.business_number, m.profile_photo_url, s.store_name, p.title, p.apply_start_date, p.apply_end_date, p.experience_date, p.content";

  public MojipPostDTO getMojipPost(int poseSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    MojipPostDTO mojipPost = new MojipPostDTO();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_MOJIP_BY_SEQ);
      pstmt.setString(1, PostGubun.MJ.getCode());
      pstmt.setInt(2, poseSeq);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        mojipPost.setPostSeq(rs.getInt("post_seq"));
        mojipPost.setMemberSeq(rs.getInt("member_seq"));
        mojipPost.setBusinessNumber(rs.getString("business_number"));
        mojipPost.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        mojipPost.setCodeName(rs.getString("food_type_names"));
        mojipPost.setName(rs.getString("tag_names"));
        mojipPost.setStoreName(rs.getString("store_name"));
        mojipPost.setTitle(rs.getString("title"));
        mojipPost.setApplyStartDate(rs.getDate("apply_start_date"));
        mojipPost.setApplyEndDate(rs.getDate("apply_end_date"));
        mojipPost.setExperienceDate(rs.getDate("experience_date"));
        mojipPost.setContent(rs.getString("content"));
        mojipPost.setNumberOfLikes(rs.getInt("number_of_likes"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return mojipPost;
  }

  public List<MojipPostDTO> getMojipPostList() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<MojipPostDTO> mojipPostList = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_MOJIP_LIST);
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
        dto.setNumberOfLikes(rs.getInt("number_of_likes"));

        mojipPostList.add(dto);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return mojipPostList;
  }

  public boolean insertMojipPost(Post post) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isInserted = false;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(INSERT_MOJIP);
      pstmt.setString(1, post.getTitle());
      pstmt.setString(2, String.valueOf(post.getMemberSeq()));
      pstmt.setDate(3, post.getApplyStartDate());
      pstmt.setDate(4, post.getApplyEndDate());
      pstmt.setDate(5, post.getExperienceDate());
      pstmt.setString(6, post.getContent());
      pstmt.setString(7, post.getGubun());

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
