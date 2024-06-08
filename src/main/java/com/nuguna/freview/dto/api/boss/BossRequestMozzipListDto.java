package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossRequestMozzipListDto {
  Integer bossSeq;
  String title;
  String applyStartDate;
  String applyEndDate;
  String experienceDate;
  String createdAt;
  Integer viewCount;

//  public BossRequestMozzipListDto(int seq, int postSeq, String title, String applyStartDate, String applyEndDate, String experienceDate, String createdAt, Integer viewCount) {
//    this.seq = seq;
//    this.postSeq = postSeq;
//    this.title = title;
//    this.applyStartDate = applyStartDate;
//    this.applyEndDate = applyEndDate;
//    this.experienceDate = experienceDate;
//    this.createdAt = createdAt;
//    this.viewCount = viewCount;
//  }

}
