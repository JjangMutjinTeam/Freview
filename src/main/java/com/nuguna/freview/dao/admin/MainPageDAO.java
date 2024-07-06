package com.nuguna.freview.dao.admin;

import com.nuguna.freview.dto.MainpageGongjiDTO;
import com.nuguna.freview.dto.MainpageMemberInfoDTO;
import com.nuguna.freview.dto.MainpageMojipDTO;
import com.nuguna.freview.dto.MainpageRequesterDTO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import com.nuguna.freview.entity.post.PostGubun;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MainPageDAO {

  private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
  private static final String DB_URL = "jdbc:mariadb://localhost:3306/freview";
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "0000";

  private static Connection conn;
  private static PreparedStatement pstmt;
  private static ResultSet rs;

  public ArrayList<MainpageMojipDTO> getMojipPostforMainPage() {

    ArrayList<MainpageMojipDTO> posts = new ArrayList<MainpageMojipDTO>();

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      String sql = "SELECT  *\n"
          + "FROM post\n"
          + "WHERE gubun = 'MJ'\n"
          + "ORDER BY updated_at DESC\n"
          + "LIMIT 3";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while(rs.next()){
          MainpageMojipDTO mmdto = new MainpageMojipDTO();
          String thumbnailPhotoUrl = rs.getString("thumbnail_photo_url");
          mmdto.setThumbnailPhotoUrl(thumbnailPhotoUrl);
          String title = rs.getString("title");
          mmdto.setTitle(title);
          int memberSeq = rs.getInt("member_seq");
          mmdto.setMemberSeq(memberSeq);
          Date applyStartDate = rs.getDate("apply_start_date");
          mmdto.setApplyStartDate(applyStartDate);
          Date applyEndDate = rs.getDate("apply_end_date");
          mmdto.setApplyEndDate(applyEndDate);
          Date experienceDate = rs.getDate("experience_date");
          mmdto.setExperienceDate(experienceDate);
          Integer viewCount = rs.getInt("view_count");
          mmdto.setViewCount(viewCount);
          posts.add(mmdto);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      try {
        if(rs!=null)rs.close();
        if(pstmt!=null)pstmt.close();
        if(conn!=null)conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return posts;
  }

  public ArrayList<MainpageRequesterDTO> getRequestersForMainPage() {

    ArrayList<MainpageRequesterDTO> requesters = new ArrayList<>();

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try{
      String sql ="SELECT *\n"
          + "FROM(SELECT ROW_NUMBER() over () as RNUM, m.member_seq, m.nickname, m.profile_photo_url\n"
          + "     FROM review r, member m\n"
          + "     WHERE r.customer_seq = m.member_seq AND\n"
          + "           r.status = 'WRITTEN'\n"
          + "    ORDER BY r.updated_at DESC) as SUB\n"
          + "WHERE RNUM <=5";

      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while(rs.next()){
        MainpageRequesterDTO mrdto = new MainpageRequesterDTO();
          int memberSeq = rs.getInt("member_seq");
          mrdto.setMemberSeq(memberSeq);
          String nickname = rs.getString("nickname");
          mrdto.setNickname(nickname);
          String profilePhotoUrl = rs.getString("profile_photo_url");
          mrdto.setProfilePhotoUrl(profilePhotoUrl);
          requesters.add(mrdto);
      }
    }catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      try {
        if(rs!=null)rs.close();
        if(pstmt!=null)pstmt.close();
        if(conn!=null)conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


    return requesters;
  }

  public ArrayList<MainpageGongjiDTO> getGongjisForMainPage() {

    ArrayList<MainpageGongjiDTO> gongjis = new ArrayList<>();

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try{
      String sql ="SELECT  post_seq,title,created_at,updated_at\n"
          + "FROM post\n"
          + "WHERE gubun = 'GJ'\n"
          + "ORDER BY updated_at DESC\n"
          + "LIMIT 5";

      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while(rs.next()){
        MainpageGongjiDTO mgdto = new MainpageGongjiDTO();
        int postSeq = rs.getInt("post_seq");
        mgdto.setPostSeq(postSeq);
        String title = rs.getString("title");
        mgdto.setTitle(title);
        Date created_at = rs.getDate("created_at");
        mgdto.setCreatedAt(created_at);
        Date updated_at = rs.getDate("updated_at");
        mgdto.setUpdatedAt(updated_at);
        gongjis.add(mgdto);
      }
    }catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      try {
        if(rs!=null)rs.close();
        if(pstmt!=null)pstmt.close();
        if(conn!=null)conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


    return gongjis;
  }

  public MainpageMemberInfoDTO getMemberInfoForMainPageHeader(int seq) {
    MainpageMemberInfoDTO minfodto = new MainpageMemberInfoDTO();

    try {
      Class.forName(DB_DRIVER_CLASS);
      conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try{
      String sql ="SELECT nickname,profile_photo_url FROM member WHERE member_seq = ?";

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1,seq);
      rs = pstmt.executeQuery();
      if(rs.next()){
        String nickname = rs.getString("nickname");
        minfodto.setNickname(nickname);
        String profilePhotoUrl = rs.getString("profile_photo_url");
        minfodto.setPhotoUrl(profilePhotoUrl);
      }
    }catch (SQLException e) {
      throw new RuntimeException(e);
    }finally {
      try {
        if(rs!=null)rs.close();
        if(pstmt!=null)pstmt.close();
        if(conn!=null)conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


    return minfodto;
  }
}
