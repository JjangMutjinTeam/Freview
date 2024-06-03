package com.nuguna.freview.entity.member.interaction;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Zzim {

  private Integer seq;
  private Integer fromMemberSeq;
  private Integer toMemberSeq;
  private Timestamp createdAt;
}
