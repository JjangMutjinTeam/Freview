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
}
