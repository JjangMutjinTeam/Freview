package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossSendLikesDto {

  Integer memberSeq;
  Integer postSeq;
  String title;
  String nickname;
  String type;

}
