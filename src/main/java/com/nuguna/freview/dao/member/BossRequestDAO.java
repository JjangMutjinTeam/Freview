package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.api.boss.BossRequestMozzipListDto;
import com.nuguna.freview.dto.api.boss.BossRequestReceivedDto;
import com.nuguna.freview.dto.api.boss.BossRequestToRequestDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BossRequestDAO {

  // 사장님 모집글 리스트
  public List<BossRequestMozzipListDto> bossMozzipList(int bossSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs;

    String sql = "SELECT p.member_seq, p.title, p.apply_start_date, p.apply_end_date"
                + ", p.experience_date, p.created_at, p.`view_count`"
                + "FROM post p "
                + " INNER JOIN member m ON p.member_seq = ? "
                + "WHERE p.member_seq = m.member_seq ";

    List<BossRequestMozzipListDto> bossMozzipList = new ArrayList<>();
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      rs = pstmt.executeQuery();
      while(rs.next()) {
//        int seq = rs.getInt("seq");
        int memberSeq = rs.getInt("member_seq");
        String title = rs.getString("title");
        String applyStartDate = rs.getString("apply_start_date");
        String applyEndDate = rs.getString("apply_end_date");
        String experienceDate = rs.getString("experience_date");
        String createDate = rs.getString("created_at");
        int viewCount = rs.getInt("view_count");
        bossMozzipList.add(
            new BossRequestMozzipListDto( memberSeq, title, applyStartDate,
                applyEndDate, experienceDate, createDate, viewCount )
        );
      }
    }  catch (SQLException e) {
      throw new RuntimeException("SQLException: 찜하기 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return bossMozzipList;
  }

  // 지원한 체험단 리스트
  public List<BossRequestReceivedDto> bossReceivedRequest(int bossSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs;

    String sql = "SELECT r.seq, r.from_member_seq, r.to_member_seq, "
        + " r.come_date, r.come_or_not, r.review_or_not, r.status, "
        + " r.benefit_detail, r.created_at, m.nickname "
        + "FROM request r "
        + "INNER JOIN member m ON r.from_member_seq = m.member_seq "
        + "INNER JOIN post p ON r.from_post = p.post_seq "
        + "INNER JOIN apply a ON r.from_post = r.from_post "
        + "WHERE p.member_seq = ? ";
    List<BossRequestReceivedDto> ReceivedRequest = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int seq = rs.getInt("seq");
        int fromMemberSeq = rs.getInt("from_member_seq");
        int toMemberSeq = rs.getInt("to_member_seq");
        String comeDate = rs.getString("come_date");
        String comeOrNot = rs.getString("come_or_not");
        String reviewOrNot = rs.getString("review_or_not");
        String status = rs.getString("status");
        String createdAt = rs.getString("created_at");
        String nickname = rs.getString("nickname");

        ReceivedRequest.add( new BossRequestReceivedDto(
            seq, fromMemberSeq, toMemberSeq, comeDate, comeOrNot, reviewOrNot, status, createdAt, nickname
          )
        );

      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 지원자 리스트 요청 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return ReceivedRequest;
  }

  // 사장님이 제안한 사람의 리스트
  public List<BossRequestToRequestDto> bossToRequest(int bossSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = "SELECT r.seq, r.from_member_seq, r.from_post, "
        + " r.come_date, r.come_or_not, r.review_or_not, "
        + " r.benefit_detail, r.status, r.created_at, m.nickname "
        + " FROM request r "
        + " INNER JOIN member m ON r.from_member_seq = m.member_seq "
        + " WHERE r.from_member_seq =  ? ";

    List<BossRequestToRequestDto> toRequestList = new ArrayList<>();
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        int seq = rs.getInt("seq");
        int fromMemberSeq = rs.getInt("from_member_seq");
        int fromPost = rs.getInt("from_post");
        String comeDate = rs.getString("come_date");
        String comeOrNot = rs.getString("come_or_not");
        String reviewOrNot = rs.getString("review_or_not");
        String benefitDetail = rs.getString("benefit_detail");
        String status = rs.getString("status");
        String createDate = rs.getString("created_at");
        String nickname = rs.getString("nickname");
        String experienceDate = getExperienceDate(fromPost);
        toRequestList.add( new BossRequestToRequestDto(seq, fromMemberSeq, fromPost, comeDate, comeOrNot, reviewOrNot, benefitDetail, status,createDate, nickname, experienceDate));
      }
    }  catch (SQLException e) {
      throw new RuntimeException("SQLException: 사장님이 체험단에게 제안한 리스트를 불러오는 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return toRequestList;
  }

  public String getExperienceDate(int postSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "SELECT experience_date from post where post_seq = ?";
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, postSeq);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        return rs.getString("experience_date");
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      closeResource(pstmt, conn);
    }

    return null;
  }


}
