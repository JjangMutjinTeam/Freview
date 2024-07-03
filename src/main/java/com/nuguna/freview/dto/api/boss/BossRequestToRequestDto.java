package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossRequestToRequestDto {
  int requestSeq;
  int fromMemberSeq;
  int from_post;
  String comeDate;
  String comeOrNot;
  String reviewOrNot;
  String benefitDetail;
  String status;
  String createdAt;
  String nickname;
  String experienceDate;
}
