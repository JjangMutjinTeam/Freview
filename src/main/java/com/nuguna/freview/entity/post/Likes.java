package com.nuguna.freview.entity.post;

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
public class Likes {

  private Integer seq;
  private Integer memberSeq;
  private Integer postSeq;
  private Timestamp createdAt;
}