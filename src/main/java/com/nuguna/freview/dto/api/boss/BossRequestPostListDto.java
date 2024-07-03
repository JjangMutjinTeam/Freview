package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossRequestPostListDto {
  String gubun;
  Integer userSeq;
  String title;
  String applyStartDate;
  String applyEndDate;
  String experienceDate;
  String createdAt;
  Integer viewCount;
}
