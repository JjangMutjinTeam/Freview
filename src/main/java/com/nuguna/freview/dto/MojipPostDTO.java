package com.nuguna.freview.dto;

import java.sql.Date;
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
public class MojipPostDTO {

  private Integer postSeq;
  private Integer memberSeq;
  private String businessNumber;
  private String profilePhotoUrl;
  private String codeName;
  private String name;
  private String storeName;
  private String title;
  private Date applyStartDate;
  private Date applyEndDate;
  private Date experienceDate;
  private String content;
  private Integer numberOfDdabong;

}