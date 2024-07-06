package com.nuguna.freview.dao.member.boss;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.api.boss.BossSendLikesDto;
import com.nuguna.freview.dto.api.boss.BossSendZzimInfoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BossSendInformDAO {

  // 사장님 -> 채험단 & 사장님 zzim
  public List<BossSendZzimInfoDto> sendZzimDAO(int Seq) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "Select m.member_seq, m.nickname "
        + "FROM member m "
        + "INNER JOIN "
        + "( SELECT z.to_member_seq"
        + "   FROM zzim z"
        + "   WHERE z.from_member_seq = ? ) zz "
        + "ON m.member_seq = zz.to_member_seq ";

    List<BossSendZzimInfoDto> zzimInfos = new ArrayList<>();
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, Seq);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        int memberSeq = rs.getInt("member_seq");
        String nickname = rs.getString("nickname");
        zzimInfos.add(new BossSendZzimInfoDto(memberSeq, nickname,"ZZIM"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 찜하기 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return zzimInfos;
  }


//   사장님 -> 사장님 & 관리자 게시글 ddabong
  public List<BossSendLikesDto> sendDdabongDAO(int seq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs;

    String sql = "SELECT p.post_seq, p.title, m.member_seq, m.nickname "
        + "FROM post p "
        + "INNER JOIN member m ON m.member_seq = p.member_seq "
        + "INNER JOIN ( "
        + "    SELECT d.member_seq, d.post_seq "
        + "    FROM ddabong d "
        + "    WHERE d.member_seq = ? "
        + ") AS d ON p.post_seq = d.post_seq " ;

    List<BossSendLikesDto> ddabongInfos = new ArrayList<>();

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, seq);
      rs = pstmt.executeQuery();
      while(rs.next()){
        int memberSeq = rs.getInt("member_seq");
        int postSeq = rs.getInt("post_seq");
        String title = rs.getString("title");
        String nickname = rs.getString("nickname");
        ddabongInfos.add(new BossSendLikesDto(memberSeq, postSeq, title, nickname, "DDABONG"));
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 따봉 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return ddabongInfos;
  }


}
