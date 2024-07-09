package com.nuguna.freview.dao.member;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.io.FileUtils;

public class ProfileImageDAO {

  // 기존 프로필 파일 삭제 및 업데이트
  public void updateMemberProfile(int memberSeq, String filePath) throws IOException {

    // 기존 파일 삭제
    String existProfilePath = getMemberProfilePath(memberSeq);

    if (existProfilePath != null) {
      File existingFile = new File(existProfilePath);
      if (existingFile.exists()) {
        FileUtils.forceDelete(existingFile);
      }
    }

    // 파일 저장
    Connection conn = null;
    PreparedStatement pstmt = null;

    String sql = "UPDATE member SET "
        + "profile_photo_url = ? "
        + "WHERE member_seq = ?";

    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, filePath);
      pstmt.setInt(2, memberSeq);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 프로필 사진 수정 도중 예외 발생", e);
    }
  }

  private String getMemberProfilePath(int memberSeq) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "SELECT profile_photo_url FROM member "
        + "WHERE member_seq = ?";

    String existProfilePath = null;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, memberSeq);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        existProfilePath = rs.getString("profile_photo_url");
      }
    } catch (SQLException e) {
      throw new RuntimeException("SQLException : 프로필 사진 조회 도중 예외 발생", e);
    } finally {
      closeResource(pstmt, conn, rs);
    }
    return existProfilePath;
  }
}
