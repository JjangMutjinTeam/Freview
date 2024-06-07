package com.nuguna.freview.dao.member;

import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.member.foodtype.FoodType;
import com.nuguna.freview.entity.member.tag.Tag;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class BossMyBrandDAO {
  // MariaDB 연결 정보
  private final String driver = "org.mariadb.jdbc.Driver";
  private final String DB_URL = "jdbc:mariadb://localhost:3306/freview";
  private final String DB_USER = "root";
  private final String DB_PW = "0000";
  private Connection conn = null;
  private PreparedStatement pstmt = null;
  private ResultSet rs = null;

  // MariaDB 연결 객체
  public BossMyBrandDAO() {
    try{
      Class.forName(driver);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
    } catch(ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // select
  public ArrayList<Member> getBrand() {

    ArrayList<Member> amb = new ArrayList<Member>();
    String sql = "SELECT a.*, b.*, c.*, e.* FROM member a "
        + "JOIN freview.member_tag b ON a.member_seq = b.'member_seq'"
        + "JOIN freview.tag c ON b.tag_seq = c.'tag_seq'"
        + "JOIN freview.member_food_type d ON a.member_seq = d.'member_seq'"
        + "JOIN freview.food_type e ON e.food_type_seq = d.'food_type_seq'"
        + "JOIN freview.store_business_info f ON a.business_number = f.'business_number'"
        + "WHERE a.gubun = 'B' AND a.mid = 'user8'" ;
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      System.out.println(rs.next());
      Member bossBrand = new Member();
      FoodType ft = new FoodType();
      Tag tt = new Tag();
      bossBrand.setNickname(rs.getString("nickname"));
      bossBrand.setEmail(rs.getString("email"));
      bossBrand.setIntroduce(rs.getString("introduce"));
      amb.add(bossBrand);
      System.out.println("member 통과");

      ft.setFoodTypeSeq(rs.getInt("food_type_seq"));
      ft.setName(rs.getString("name"));

      tt.setTagSeq(rs.getInt("tag_seq"));
      tt.setGubun(rs.getString("gubun"));
      tt.setName(rs.getString("name"));



      System.out.println("여기까지는 됨");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Close resources
      try {
        if (rs != null)
          rs.close();
        if (pstmt != null)
          pstmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return amb;
  }

  public static void main(String[] args) {

  }
}