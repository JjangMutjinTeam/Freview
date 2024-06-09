package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.dto.api.boss.BossReceivedZzimInfoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BossReceviedInformDAO {

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
        zzimInfos.add(new BossReceivedZzimInfoDto(memberSeq, nickname));
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException: 찜하기 도중 문제가 발생했습니다.", e);
    } finally {
      closeResource(pstmt, conn);
    }
    return zzimInfos;
  }

}
