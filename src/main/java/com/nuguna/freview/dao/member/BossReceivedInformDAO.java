package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.api.boss.BossReceivedDdabongDto;
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
        + "FROM MEMBER m "
        + "INNER JOIN "
        + " ( SELECT z.from_member_seq "
        + " FROM ZZIM z "
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
  public List<BossReceivedDdabongDto> receivedDdabongDAO(int bossSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs;

    String sql = "SELECT p.post_seq, p.title, d.`member_seq`"
        + "FROM post p "
        + "INNER JOIN ( "
        + "    SELECT d.member_seq, d.post_seq "
        + "    FROM ddabong d "
        + "    WHERE d.member_seq = ? "
        + ") AS d ON p.post_seq = d.post_seq " ;

    List<BossReceivedDdabongDto> ddabongInfos = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, bossSeq);
      rs = pstmt.executeQuery();

      while (rs.next()){
        int memberSeq = rs.getInt("member_seq");
        int postSeq = rs.getInt("post_seq");
        String title = rs.getString("title");
        ddabongInfos.add(new BossReceivedDdabongDto(memberSeq, postSeq, title, "DDABONG"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 따봉 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return ddabongInfos;

  }

}
