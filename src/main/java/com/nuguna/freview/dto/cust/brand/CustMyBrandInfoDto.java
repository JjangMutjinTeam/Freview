package com.nuguna.freview.dto.cust.brand;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CustMyBrandInfoDto {

  private String profilePhotoUrl;
  private String ageGroup;
  private String introduce;
  private Integer zzimCount;
  private List<String> foodTypes;
  private List<String> tagInfos;
  private List<ReviewLogInfoDto> reviewInfos;


  @Getter
  @ToString
  @AllArgsConstructor
  public static class ReviewLogInfoDto {

    private String reviewStatus;
    private String storeName;
    private Date visitedDate;
    private String reviewUrl;
  }

}
