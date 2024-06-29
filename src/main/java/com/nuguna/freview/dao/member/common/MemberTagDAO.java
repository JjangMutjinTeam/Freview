package com.nuguna.freview.dao.member.common;

import static com.nuguna.freview.util.DbUtil.closeResource;
import static com.nuguna.freview.util.DbUtil.getConnection;

import com.nuguna.freview.entity.member.MemberGubun;
import com.nuguna.freview.entity.member.tag.BossTag;
import com.nuguna.freview.entity.member.tag.CustomerTag;
import com.nuguna.freview.entity.member.tag.TagItem;
import com.nuguna.freview.exception.IllegalTagException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberTagDAO {

  public void updateTags(int memberSeq, List<String> tagNames, MemberGubun memberGubun) {

    List<TagItem> tags = null;
    try {
      if (memberGubun.isCust()) {
        tags = tagNames.stream()
            .map(CustomerTag::new)
            .collect(Collectors.toList());
      } else if (memberGubun.isBoss()) {
        tags = tagNames.stream()
            .map(BossTag::new)
            .collect(Collectors.toList());
      }
    } catch (IllegalTagException e) {
      throw e;
    }

    Connection conn = null;
    PreparedStatement deletePstmt = null;
    PreparedStatement selectPstmt = null;
    PreparedStatement insertPstmt = null;
    ResultSet rs = null;

    String deleteSql = "DELETE FROM MEMBER_TAG "
        + "WHERE member_seq = ?";

    // foodTypeGubuns 리스트의 크기만큼 물음표를 생성
    String placeholders = tags.stream()
        .map(t -> "?")
        .collect(Collectors.joining(", "));

    log.info("memberGubun.getCode() = " + memberGubun.getCode());
    String selectSql = "SELECT tag_seq FROM TAG "
        + "WHERE tag.gubun = '" + memberGubun.getCode() + "' AND "
        + "tag.name IN (" + placeholders + ")";

    String insertSql = "INSERT INTO MEMBER_TAG (member_seq, tag_seq) "
        + "VALUES (?,?)";

    try {
      conn = getConnection();

      // 트랜잭션을 시작
      conn.setAutoCommit(false);

      // 1. 기존 Member - Tag 매핑 삭제
      deletePstmt = conn.prepareStatement(deleteSql);
      deletePstmt.setInt(1, memberSeq);
      deletePstmt.executeUpdate();

      // 2. Tags에 해당하는 tag_seq 조회
      selectPstmt = conn.prepareStatement(selectSql);

      int index = 1;
      for (TagItem tagItem : tags) {
        selectPstmt.setString(index++, tagItem.getTagName());
      }
      log.info("index = " + index);

      List<Integer> tagSeqs = new ArrayList<>();
      if (index > 1) { // 뭐라도 하나 들어있어야 쿼리 실행 가능
        rs = selectPstmt.executeQuery();
        log.info("select 성공");
        while (rs.next()) {
          tagSeqs.add(rs.getInt("tag_seq"));
        }
      }

      log.info(tagSeqs.toString());

      // 3. 선택된 tag_seq들을 Member와 매핑해준다.
      insertPstmt = conn.prepareStatement(insertSql);
      for (Integer tagSeq : tagSeqs) {
        insertPstmt.setInt(1, memberSeq);
        insertPstmt.setInt(2, tagSeq);
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
      throw new RuntimeException("SQLException : 태그 변경 도중 예외 발생", e);
    } finally {
      closeResource(rs);
      closeResource(deletePstmt);
      closeResource(selectPstmt);
      closeResource(insertPstmt, conn);
    }
  }
}
