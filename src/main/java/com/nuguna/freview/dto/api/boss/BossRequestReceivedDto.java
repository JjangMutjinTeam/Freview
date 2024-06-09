package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossRequestReceivedDto {

  int seq;
  int fromMemberSeq;
  int toMemberSeq;
  String comeDate;
  String comeOrNot;
  String reviewOrNot;
  String status;
  String createdAt;
  String nickname;
}
