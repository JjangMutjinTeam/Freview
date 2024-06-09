package com.nuguna.freview.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainpageGongjiDTO {
  private int postSeq;
  private String title;
  private Date createdAt;
  private Date updatedAt;
}
