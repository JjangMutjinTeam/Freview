package com.nuguna.freview.dto.cust.brand;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustBrandInfoDto {

  private String profilePhotoUrl;
  private String nickname;
  private String ageGroup;
  private String introduce;
  private List<String> foodTypes;
  private List<String> tagInfos;
  private List<ReviewInfoDto> reviewInfos;
  private int zzimCount;

}
