package com.nuguna.freview.dao.post;

import static com.nuguna.freview.config.DbConfig.DB_PW;
import static com.nuguna.freview.config.DbConfig.DB_URL;
import static com.nuguna.freview.config.DbConfig.DB_USER;
import static com.nuguna.freview.config.DbConfig.DRIVER_NAME;

import com.nuguna.freview.entity.post.Post;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostDAO {

  private final String SELECT_POST_BY_PAGING = " SELECT post_seq, title, view_count, created_at "
      + "FROM post "
      + "WHERE gubun = ? and post_seq < ? "
      + "ORDER BY post_seq DESC "
      + "limit ?";

  private final String COUNT_POST = "SELECT COUNT(*) FROM freview.POST WHERE gubun = ?";

  private final String INSERT_POST = "INSERT INTO post(title, content, gubun, created_at, updated_at, member_seq) VALUES(?, ?, ?, ?, ?, ?)";

  private final String SELECT_POST_BY_SEQ = " SELECT post_seq, member_seq, title, content, view_count, created_at, updated_at from post WHERE post_seq = ?";

  public Post selectPostByPostSeq(int postSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Post post = new Post();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(SELECT_POST_BY_SEQ);
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
      pstmt = conn.prepareStatement(INSERT_POST);
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

  public List<Post> selectPostByCursorPaging(String gubun, int previousPostSeq, int limit) {
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
    }

    return countTotalPosts;
  }

  private Connection getConnection() {
    Connection conn = null;
    try {
      Class.forName(DRIVER_NAME);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    } catch (ClassNotFoundException e) {
      log.error("JDBC Driver not found");
    } catch (SQLException e) {
      log.error("connection failed");
    }
    return conn;
  }

  public void closeResource(PreparedStatement pstmt, Connection conn, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void closeResource(PreparedStatement pstmt, Connection conn) {
    try {
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
