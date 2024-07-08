package com.nuguna.freview.dto.cust.brand;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustMyZzimStoreDto {

  private Integer bossSeq;
  private String storeName;
  private String storeLoc;
  private List<String> foodTypes;
  private List<String> tagInfos;
}
