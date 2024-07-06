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

  //TODO: sql 성능 개선

  public MojipPostDTO getMojipPostOne(int postSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    MojipPostDTO mojipPost = new MojipPostDTO();

    String sql = "SELECT " +
        "    p.post_seq, " +
        "    m.member_seq, " +
        "    m.gubun, " +
        "    m.business_number, " +
        "    m.profile_photo_url, " +
        "    m.store_location, " +
        "    GROUP_CONCAT(DISTINCT ft.name ORDER BY ft.name ASC SEPARATOR ', ') AS food_type_names, " +
        "    GROUP_CONCAT(DISTINCT t.name ORDER BY t.name ASC SEPARATOR ', ') AS tag_names, " +
        "    s.store_name, " +
        "    p.title, " +
        "    p.apply_start_date, " +
        "    p.apply_end_date, " +
        "    p.experience_date, " +
        "    p.content, " +
        "    (SELECT COUNT(*) FROM likes l WHERE l.post_seq = p.post_seq) AS number_of_likes " +
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

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, PostGubun.MJ.getCode());
      pstmt.setInt(2, postSeq);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        mojipPost.setPostSeq(rs.getInt("post_seq"));
        mojipPost.setMemberSeq(rs.getInt("member_seq"));
        mojipPost.setBusinessNumber(rs.getString("business_number"));
        mojipPost.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        mojipPost.setStoreLocation(rs.getString("store_location"));
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

  public List<MojipPostDTO> getMojipPostList(int previousPostSeq, int limit) {
    String sql = "SELECT " +
        "    p.post_seq, " +
        "    m.member_seq, " +
        "    m.gubun, " +
        "    m.business_number, " +
        "    m.profile_photo_url, " +
        "    m.store_location, " +
        "    GROUP_CONCAT(DISTINCT ft.name ORDER BY ft.name ASC SEPARATOR ', ') AS food_type_names, " +
        "    GROUP_CONCAT(DISTINCT t.name ORDER BY t.name ASC SEPARATOR ', ') AS tag_names, " +
        "    s.store_name, " +
        "    p.title, " +
        "    p.apply_start_date, " +
        "    p.apply_end_date, " +
        "    p.experience_date, " +
        "    p.content, " +
        "    (SELECT COUNT(*) FROM likes l WHERE l.post_seq = p.post_seq) AS number_of_likes " +
        "FROM " +
        "    post p " +
        "LEFT JOIN member m ON p.member_seq = m.member_seq " +
        "LEFT JOIN member_food_type mft ON m.member_seq = mft.member_seq " +
        "LEFT JOIN food_type ft ON mft.food_type_seq = ft.food_type_seq " +
        "LEFT JOIN member_tag mt ON m.member_seq = mt.member_seq " +
        "LEFT JOIN tag t ON mt.tag_seq = t.tag_seq " +
        "LEFT JOIN store_business_info s ON m.business_number = s.business_number " +
        "WHERE p.gubun = ? AND p.post_seq < ? " +
        "GROUP BY p.post_seq, m.member_seq, m.gubun, m.business_number, m.profile_photo_url, s.store_name, p.title, p.apply_start_date, p.apply_end_date, p.experience_date, p.content " + // 공백 추가
        "ORDER BY p.post_seq DESC " +
        "LIMIT ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<MojipPostDTO> mojipPostList = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, PostGubun.MJ.getCode());
      pstmt.setInt(2, previousPostSeq);
      pstmt.setInt(3, limit);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        MojipPostDTO dto = new MojipPostDTO();
        dto.setPostSeq(rs.getInt("post_seq"));
        dto.setMemberSeq(rs.getInt("member_seq"));
        dto.setBusinessNumber(rs.getString("business_number"));
        dto.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        dto.setStoreLocation(rs.getString("store_location"));
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

  public List<MojipPostDTO> getMojipPostList(int previousPostSeq, int limit, String searchWord) {
    String sql = "SELECT " +
        "    p.post_seq, " +
        "    m.member_seq, " +
        "    m.gubun, " +
        "    m.business_number, " +
        "    m.profile_photo_url, " +
        "    m.store_location, " +
        "    GROUP_CONCAT(DISTINCT ft.name ORDER BY ft.name ASC SEPARATOR ', ') AS food_type_names, " +
        "    GROUP_CONCAT(DISTINCT t.name ORDER BY t.name ASC SEPARATOR ', ') AS tag_names, " +
        "    s.store_name, " +
        "    p.title, " +
        "    p.apply_start_date, " +
        "    p.apply_end_date, " +
        "    p.experience_date, " +
        "    p.content, " +
        "    (SELECT COUNT(*) FROM likes l WHERE l.post_seq = p.post_seq) AS number_of_likes " +
        "FROM " +
        "    post p " +
        "LEFT JOIN member m ON p.member_seq = m.member_seq " +
        "LEFT JOIN member_food_type mft ON m.member_seq = mft.member_seq " +
        "LEFT JOIN food_type ft ON mft.food_type_seq = ft.food_type_seq " +
        "LEFT JOIN member_tag mt ON m.member_seq = mt.member_seq " +
        "LEFT JOIN tag t ON mt.tag_seq = t.tag_seq " +
        "LEFT JOIN store_business_info s ON m.business_number = s.business_number " +
        "WHERE p.gubun = ? AND p.post_seq < ? " +
        "AND (p.title LIKE CONCAT('%', ?, '%') OR p.content LIKE CONCAT('%', ?, '%') OR ft.name LIKE CONCAT('%', ?, '%') OR t.name LIKE CONCAT('%', ?, '%') OR m.store_location LIKE CONCAT('%', ?, '%')) " +
        "GROUP BY p.post_seq, m.member_seq, m.gubun, m.business_number, m.profile_photo_url, s.store_name, p.title, p.apply_start_date, p.apply_end_date, p.experience_date, p.content " +
        "ORDER BY p.post_seq DESC " +
        "LIMIT ?";


    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<MojipPostDTO> mojipPostList = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      int paramIndex = 1;
      pstmt.setString(paramIndex++, PostGubun.MJ.getCode());
      pstmt.setInt(paramIndex++, previousPostSeq);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setString(paramIndex++, searchWord);
      pstmt.setInt(paramIndex, limit);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        MojipPostDTO dto = new MojipPostDTO();
        dto.setPostSeq(rs.getInt("post_seq"));
        dto.setMemberSeq(rs.getInt("member_seq"));
        dto.setBusinessNumber(rs.getString("business_number"));
        dto.setProfilePhotoUrl(rs.getString("profile_photo_url"));
        dto.setStoreLocation(rs.getString("store_location"));
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

    String sql = "INSERT INTO post(title, member_seq, apply_start_date, apply_end_date, experience_date, content, gubun) VALUES(?, ?, ?, ?, ?, ?, ?) ";

    boolean isInserted = false;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
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
