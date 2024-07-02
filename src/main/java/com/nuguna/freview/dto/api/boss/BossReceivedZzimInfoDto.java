package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossReceivedZzimInfoDto {

  Integer memberSeq;
  String nickname;
  Integer toMemberSeq;
  String type;

}
