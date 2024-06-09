package com.nuguna.freview.dao.post;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.post.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostDAO {

  private final String SELECT_NOTICE_BY_SEQ = " SELECT post_seq, member_seq, title, content, view_count, created_at, updated_at from post WHERE post_seq = ?";

  private final String UPDATE_POST_BY_SEQ = "UPDATE post SET title = ?, content = ?, updated_at = ? WHERE post_seq = ?";

  private final String DELETE_POST_BY_SEQ = "DELETE FROM post WHERE post_seq = ?";

  private final String SELECT_POST_LIKED = "SELECT count(*) from ddabong where member_seq = ? and post_seq = ?";

  public boolean isLikedPost(int memberSeq, int postSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isPostLiked = false;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_POST_LIKED);
      pstmt.setInt(1, memberSeq);
      pstmt.setInt(2, postSeq);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        int rows = rs.getInt(1);
        if (rows > 0) {
          isPostLiked = true;
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return isPostLiked;
  }

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

}
