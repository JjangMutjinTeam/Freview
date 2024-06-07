package com.nuguna.freview.dto;

import com.nuguna.freview.entity.member.AgeGroup;
import com.nuguna.freview.entity.member.MemberGubun;
import com.nuguna.freview.entity.member.foodtype.FoodTypeGubun;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BossBrandDTO {
  private Integer memberSeq;
  private MemberGubun gubun;
  private String mid;
  private String mpw;
  private String nickname;
  private String email;
  private String introduce;
  private String businessNumber;
  private String storeLoc;
  private String profilePhotoUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  private Integer foodTypeSeq;
  private FoodTypeGubun name;

  public String getGubun() {
    return gubun != null ? gubun.getCode() : null;
  }

  public void setGubun(String gubun) {
    this.gubun = MemberGubun.from(gubun);
  }


}
