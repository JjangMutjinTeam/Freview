package com.nuguna.freview.dao.member.common;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.member.foodtype.FoodTypeGubun;
import com.nuguna.freview.exception.IllegalFoodTypeException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberFoodTypeDAO {

  public void updateMemberFoodTypes(int memberSeq, List<String> foodTypeNames) {
    log.info("foodTypeNames = " + foodTypeNames.toString());
    List<FoodTypeGubun> foodTypeGubuns;
    try {
      foodTypeGubuns = foodTypeNames.stream()
          .map(FoodTypeGubun::fromName)
          .collect(Collectors.toList());
    } catch (IllegalFoodTypeException e) {
      throw e;
    }

    Connection conn = null;
    PreparedStatement deletePstmt = null;
    PreparedStatement selectPstmt = null;
    PreparedStatement insertPstmt = null;
    ResultSet rs = null;

    String deleteSql = "DELETE FROM member_food_type "
        + "WHERE member_seq = ?";

    // foodTypeGubuns 리스트의 크기만큼 물음표를 생성
    String placeholders = foodTypeGubuns.stream()
        .map(g -> "?")
        .collect(Collectors.joining(", "));

    String selectSql = "SELECT food_type_seq FROM FOOD_TYPE "
        + "WHERE name IN (" + placeholders + ")";

    String insertSql = "INSERT INTO member_food_type (member_seq, food_type_seq) "
        + "VALUES (?,?)";

    try {
      conn = getConnection();

      // 트랜잭션을 시작
      conn.setAutoCommit(false);

      // 1. 기존 Member - FoodType 매핑 삭제
      deletePstmt = conn.prepareStatement(deleteSql);
      deletePstmt.setInt(1, memberSeq);
      deletePstmt.executeUpdate();

      // 2. foodTypeGubun들에 해당하는 food_type_seq 조회
      selectPstmt = conn.prepareStatement(selectSql);

      int index = 1;
      for (FoodTypeGubun foodTypeGubun : foodTypeGubuns) {
        selectPstmt.setString(index++, foodTypeGubun.getName());
      }

      List<Integer> foodTypeSeqs = new ArrayList<>();
      if (index > 1) {
        rs = selectPstmt.executeQuery();
        log.info("select 성공");
        while (rs.next()) {
          foodTypeSeqs.add(rs.getInt("food_type_seq"));
        }
      }

      log.info(foodTypeSeqs.toString());

      // 3. 선택된 FoodTypeSeq들을 Member와 매핑해준다.
      insertPstmt = conn.prepareStatement(insertSql);
      for (Integer foodTypeSeq : foodTypeSeqs) {
        insertPstmt.setInt(1, memberSeq);
        insertPstmt.setInt(2, foodTypeSeq);
        insertPstmt.addBatch();
      }
      insertPstmt.executeBatch();

      // 트랜잭션 커밋
      conn.commit();
    } catch (SQLException e) {
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException rollbackEx) {
          throw new RuntimeException("예외가 발생했으나 롤백에 실패함");
        }
      }
      throw new RuntimeException("SQLException : 활동 분야 변경 도중 예외 발생", e);
    } finally {
      closeResource(rs);
      closeResource(deletePstmt);
      closeResource(selectPstmt);
      closeResource(insertPstmt, conn);
    }
  }
}
