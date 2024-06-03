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
public class Apply {

  private int seq;
  private int memberSeq;
  private int postSeq;
  private Timestamp createdAt;
}