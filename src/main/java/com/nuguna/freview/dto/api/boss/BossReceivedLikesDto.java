package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossReceivedLikesDto {
  Integer seq;
  String nickname;
  Integer postSeq;
  String title;
  String type;
}
