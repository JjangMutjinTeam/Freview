package com.nuguna.freview.dao.member.cust.page;

import com.nuguna.freview.dto.cust.brand.CustMyBrandInfoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustBrandInfoDAO {

  public CustMyBrandInfoDto getCustBrandInfo(int memberSeq) {

    Connection conn = null;
    PreparedStatement basicInfopstmt = null;
    ResultSet rs = null;

    // TODO : 한 번의 쿼리에서 모든 데이터를 가져올 수 있다면 가져올 수 있도록, 쿼리 최적화 필요.

    // 자기소개, 연령대, 닉네임, 프로필 사진 URL, 나를 찜한 사람 수
    String basicInfoSql = "SELECT\n"
        + "    mm.introduce,\n"
        + "    mm.age_group,\n"
        + "    mm.nickname,\n"
        + "    mm.profile_photo_url,\n"
        + "    COUNT(z.from_member_seq) as zzimCount\n"
        + "FROM\n"
        + "    (SELECT\n"
        + "         m.member_seq,\n"
        + "         m.introduce,\n"
        + "         m.age_group,\n"
        + "         m.nickname,\n"
        + "         m.profile_photo_url\n"
        + "     FROM\n"
        + "         member as m\n"
        + "     WHERE\n"
        + "             m.member_seq = 59) as mm\n"
        + "        JOIN\n"
        + "    ZZIM as z\n"
        + "    ON\n"
        + "        mm.member_seq = z.to_member_seq;";

    // 활동분야 이름들 가져오기
//    String reviewSql = "SELECT"

    return null;
  }
}
