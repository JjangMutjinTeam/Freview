package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.api.boss.BossRequestMozzipListDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BossRequestDAO {

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
        bossSeq = rs.getInt("member_seq");
        String title = rs.getString("title");
        String applyStartDate = rs.getString("apply_start_date");
        String applyEndDate = rs.getString("apply_end_date");
        String experienceDate = rs.getString("experience_date");
        String createDate = rs.getString("created_at");
        int viewCount = rs.getInt("view_count");
        bossMozzipList.add(
            new BossRequestMozzipListDto( bossSeq, title, applyStartDate,
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
}
