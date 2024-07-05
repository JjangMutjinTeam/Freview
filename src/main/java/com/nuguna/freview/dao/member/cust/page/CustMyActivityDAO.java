package com.nuguna.freview.dao.member.cust.page;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.cust.activitylog.CustMyLikePostDto;
import com.nuguna.freview.dto.cust.brand.CustMyZzimStoreDto;
import com.nuguna.freview.dto.cust.brand.CustZzimedMeStoreDto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

// REST API 방식으로 구현해야할듯 ( URL 이동 X, 뷰만 바뀌게 )
// 1. 내가 좋아요한 글 가져오기 ( 기본값 )
// 2. 내가 찜한 스토어 가져오기
// 3. 나를 찜한 스토어 가져오기
@Slf4j
public class CustMyActivityDAO {

  /*
  좋아요한 글
   */
  public List<CustMyLikePostDto> getLikePosts(int memberSeq) {

    Connection conn = null;
    PreparedStatement postSeqPstmt = null;
    PreparedStatement likePostsPstmt = null;
    ResultSet rs = null;

    // 1. 유저가 좋아하는 post들의 seq들 가져오기
    String postSeqSql = "SELECT post_seq FROM likes "
        + "WHERE member_seq = ? "
        + "ORDER BY created_at DESC"; // 페이지네이션 관련 SQL 추가 필요

    // post_seq, title(20), content(30), COUNT(post_like)
    String likePostsSql = "SELECT\n"
        + "    p.*,\n"
        + "    COUNT(*) as likes\n"
        + "FROM\n"
        + "    (SELECT\n"
        + "        post_seq,\n"
        + "        title,\n"
        + "        content,\n"
        + "        created_at"
        + "    FROM\n"
        + "        post p\n"
        + "    WHERE\n"
        + "        post_seq = ?) p\n"
        + "    INNER JOIN\n"
        + "        likes l\n"
        + "    ON\n"
        + "        p.post_seq = l.post_seq;";

    List<Integer> postSeqs = new ArrayList<>();
    List<CustMyLikePostDto> myLikePostDtos = new ArrayList<>();
    try {
      conn = getConnection();

      // 유저가 좋아하는 글들의 seq를 가져온다.
      postSeqPstmt = conn.prepareStatement(postSeqSql);
      postSeqPstmt.setInt(1, memberSeq);
      rs = postSeqPstmt.executeQuery();

      while (rs.next()) {
        postSeqs.add(rs.getInt("post_seq"));
      }

      // Fetch food types
      for (int pSeq : postSeqs) {
        likePostsPstmt = conn.prepareStatement(likePostsSql);
        likePostsPstmt.setInt(1, pSeq);
        rs = likePostsPstmt.executeQuery();
        if (rs.next()) {
          Integer seq = rs.getInt("post_seq");
          String title = rs.getString("title");
          String content = rs.getString("content");
          Date createdAt = rs.getDate("created_at");
          Integer likes = rs.getInt("likes");
          myLikePostDtos.add(
              new CustMyLikePostDto(seq, title, content, likes, createdAt));
        }
      }
    } catch (SQLException e) {
      log.error("SQL error", e);
    } finally {
      closeResource(rs);
      closeResource(likePostsPstmt);
      closeResource(postSeqPstmt, conn);
    }
    return myLikePostDtos;
  }

  public List<CustMyZzimStoreDto> getZzimStores(int membeSeq) {
    return null;
  }

  public List<CustZzimedMeStoreDto> getZzimedMeStores(int membeSeq) {
    return null;
  }

}
