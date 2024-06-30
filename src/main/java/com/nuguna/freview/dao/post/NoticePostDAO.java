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

public class NoticePostDAO {

  public boolean insertNoticePost(Post post) {
    boolean isInserted = false;
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "INSERT INTO post(title, content, gubun, created_at, updated_at, member_seq) VALUES(?, ?, ?, ?, ?, ?)";
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
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

  public List<Post> getNoticeByPage(String gubun, int pageNumber, int numberOfPostsPerPage) {
    String sql = "SELECT post_seq, title, view_count, created_at, updated_at FROM post where gubun = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List<Post> posts = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, gubun);
      pstmt.setLong(2, numberOfPostsPerPage);
      pstmt.setLong(3, (pageNumber - 1) * numberOfPostsPerPage);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Post post = new Post();
        post.setPostSeq(rs.getInt("post_seq"));
        post.setTitle(rs.getString("title"));
        post.setViewCount(rs.getInt("view_count"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));

        posts.add(post);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn, rs);
    }

    return posts;
  }
}
