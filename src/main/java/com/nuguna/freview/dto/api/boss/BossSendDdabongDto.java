package com.nuguna.freview.dto.api.boss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossSendDdabongDto {

  Integer memberSeq;
  Integer postSeq;
  String title;

  public BossSendDdabongDto(int memberSeq, int postSeq, String title) {
    this.memberSeq = memberSeq;
    this.postSeq = postSeq;
    this.title = title;
  }

}
