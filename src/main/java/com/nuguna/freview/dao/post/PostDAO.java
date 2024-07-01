package com.nuguna.freview.dao.post;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.post.Likes;
import com.nuguna.freview.entity.post.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostDAO {

  public boolean deleteLikes(Likes likes) {
    String sql = "DELETE FROM likes WHERE member_seq = ? and post_seq = ?"; //TODO: likesSeq을 조건으로 사용할지, postSeq를 조건으로 사용할지 고민
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isDeleted = false;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, likes.getMemberSeq());
      pstmt.setInt(2, likes.getPostSeq());

      int rows = pstmt.executeUpdate();

      if (rows > 0) {
        isDeleted = true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return isDeleted;
  }

  public boolean insertLikes(Likes likes) {
    String sql = "INSERT into likes(member_seq, post_seq, created_at) values(?, ?, ?)";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isInserted = false;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, likes.getMemberSeq());
      pstmt.setInt(2, likes.getPostSeq());
      pstmt.setTimestamp(3, likes.getCreatedAt());
      int rows = pstmt.executeUpdate();

      if (rows> 0) {
        isInserted = true;
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return isInserted;
  }

  public boolean isLikedPost(int memberSeq, int postSeq) {
    String sql = "SELECT count(*) from likes where member_seq = ? and post_seq = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    boolean isPostLiked = false;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
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
    String sql = "DELETE FROM post WHERE post_seq = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);

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
    String sql = "UPDATE post SET title = ?, content = ?, updated_at = ? WHERE post_seq = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);

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
    String sql = " SELECT post_seq, member_seq, title, content, view_count, created_at, updated_at from post WHERE post_seq = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Post post = new Post();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
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


  public int getTotalPostsCount(String gubun) {
    String sql = "SELECT count(*) FROM post where gubun = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    int count = 0;

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, gubun);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return count;
  }
}
