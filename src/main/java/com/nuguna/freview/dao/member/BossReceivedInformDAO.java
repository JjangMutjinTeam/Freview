package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.api.boss.BossReceivedLikesDto;
import com.nuguna.freview.dto.api.boss.BossReceivedZzimInfoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BossReceivedInformDAO {
  
  // 보낸 따봉
  public List<BossReceivedZzimInfoDto> receivedZzimDAO(int bossSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "SELECT m.member_seq, m.nickname "
        + "FROM member m "
        + "INNER JOIN "
        + " ( SELECT z.from_member_seq "
        + " FROM zzim z "
        + " WHERE z.to_member_seq = ? ) zz"
        + " ON m.member_seq = zz.from_member_seq";

    List<BossReceivedZzimInfoDto> zzimInfos = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        int memberSeq = rs.getInt("member_seq");
        String nickname = rs.getString("nickname");
        zzimInfos.add(new BossReceivedZzimInfoDto(memberSeq, nickname, "ZZIM"));
        System.out.println(zzimInfos.get(0));
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 찜하기 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return zzimInfos;
  }


  // 받은 따봉
  // TODO : 쿼리문 수정 필요!
  public List<BossReceivedLikesDto> receivedDdabongDAO(int bossSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs;

    String sql = "SELECT m.member_seq,m.nickname, d.post_seq, p.title "
        + "FROM member m "
        + "INNER JOIN ddabong d ON m.member_seq = d.`member_seq`"
        + "INNER JOIN post p ON d.post_seq = p.`post_seq`"
        + "WHERE p.member_seq = ? ";

    List<BossReceivedLikesDto> ddabongInfos = new ArrayList<>();
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      rs = pstmt.executeQuery();
      while (rs.next()){
        int memberSeq = rs.getInt("member_seq");
        int postSeq = rs.getInt("post_seq");
        String title = rs.getString("title");
        String nickname = rs.getString("nickname");
        ddabongInfos.add(new BossReceivedLikesDto(memberSeq, nickname, postSeq, title, "DDABONG"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 따봉 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return ddabongInfos;

  }

}
