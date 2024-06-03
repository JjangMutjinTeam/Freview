package com.nuguna.freview.entity.member.interaction;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zzim {

  private int seq;
  private int fromMemberSeq;
  private int toMemberSeq;
  private Timestamp createdAt;
}
