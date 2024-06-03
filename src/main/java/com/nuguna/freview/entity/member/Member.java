package com.nuguna.freview.entity.member;

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
public class Member {

  private Integer memberSeq;
  private MemberGubun gubun;
  private String mid;
  private String mpw;
  private String nickname;
  private String email;
  private AgeGroup ageGroup;
  private String introduce;
  private String businessNumber;
  private String storeLoc;
  private String profilePhotoUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  public String getGubun() {
    return gubun != null ? gubun.getCode() : null;
  }

  public void setGubun(String gubun) {
    this.gubun = MemberGubun.from(gubun);
  }

  public String getAgeGroup() {
    return ageGroup != null ? ageGroup.getAgeGroup() : null;
  }

  public void setAgeGroup(String ageGroup) {
    this.ageGroup = AgeGroup.from(ageGroup);
  }
}