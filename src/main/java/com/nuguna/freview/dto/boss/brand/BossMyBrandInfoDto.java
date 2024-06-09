package com.nuguna.freview.dto.boss.brand;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BossMyBrandInfoDto {

  private String profilePhotoUrl;
  private String introduce;
  private String storeLoc;
  private Integer zzimCount;
  private String storeName;
  private List<String> foodTypes;
  private List<String> tagInfos;

}
