package com.nuguna.freview.entity.member;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  private int memberSeq;
  private String gubun;
  private String uid;
  private String upw;
  private String nickname;
  private String email;
  private String age;
  private String introduce;
  private String businessNumber;
  private String storeLoc;
  private String profilePhotoUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;
}