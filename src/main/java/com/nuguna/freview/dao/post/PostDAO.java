package com.nuguna.freview.dao.post;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.post.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostDAO {

  private final String SELECT_POST_BY_PAGING = " SELECT post_seq, title, view_count, created_at, updated_at "
      + "FROM post "
      + "WHERE gubun = ? and post_seq < ? "
      + "ORDER BY post_seq DESC "
      + "limit ?";

  private final String COUNT_POST = "SELECT COUNT(*) FROM post WHERE gubun = ?";

  private final String INSERT_NOTICE = "INSERT INTO post(title, content, gubun, created_at, updated_at, member_seq) VALUES(?, ?, ?, ?, ?, ?)";

  private final String SELECT_NOTICE_BY_SEQ = " SELECT post_seq, member_seq, title, content, view_count, created_at, updated_at from post WHERE post_seq = ?";

  private final String UPDATE_POST_BY_SEQ = "UPDATE post SET title = ?, content = ?, updated_at = ? WHERE post_seq = ?";

  private final String DELETE_POST_BY_SEQ = "DELETE FROM post WHERE post_seq = ?";

  public boolean deletePost(int postSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(DELETE_POST_BY_SEQ);

      pstmt.setInt(1, postSeq);

      int result = pstmt.executeUpdate();
      return result > 0;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }
  }

  public boolean updatePost(Post post) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(UPDATE_POST_BY_SEQ);

        pstmt.setString(1, post.getTitle());
        pstmt.setString(2, post.getContent());
        pstmt.setTimestamp(3, post.getUpdatedAt());
        pstmt.setInt(4, post.getPostSeq());

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }
  }

  public Post selectPostByPostSeq(int postSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Post post = new Post();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_NOTICE_BY_SEQ);
      pstmt.setInt(1, postSeq);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        post.setPostSeq(rs.getInt("post_seq"));
        post.setMemberSeq(rs.getInt("member_seq"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setViewCount(rs.getInt("view_count"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));

      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return post;
  }

  public boolean insertPost(Post post) {
    boolean isInserted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(INSERT_NOTICE);
      pstmt.setString(1, post.getTitle());
      pstmt.setString(2, post.getContent());
      pstmt.setString(3, post.getGubun());
      pstmt.setTimestamp(4, post.getCreatedAt());
      pstmt.setTimestamp(5, post.getUpdatedAt());
      pstmt.setInt(6, post.getMemberSeq());

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

  public List<Post> selectNoticePostByCursorPaging(String gubun, int previousPostSeq, int limit) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    System.out.println("previousPostSeq: " + previousPostSeq);

    List<Post> list = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_POST_BY_PAGING);
      pstmt.setString(1, gubun);
      pstmt.setInt(2, previousPostSeq);
      pstmt.setInt(3, limit);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Post post = new Post();
        post.setPostSeq(rs.getInt("post_seq"));
        post.setTitle(rs.getString("title"));
        post.setViewCount(rs.getInt("view_count"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));

        list.add(post);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return list;
  }

  public int countTotalPost(String gubun) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    int countTotalPosts = 0;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(COUNT_POST);
      pstmt.setString(1, gubun);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        countTotalPosts = rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return countTotalPosts;
  }
}
