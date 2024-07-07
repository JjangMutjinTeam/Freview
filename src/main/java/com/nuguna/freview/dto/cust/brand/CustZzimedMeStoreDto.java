package com.nuguna.freview.dto.cust.brand;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustZzimedMeStoreDto {

  // CustMyZzimStore 와 담는 내용이 같지만,
  // 이름을 통한 명시성 및
  // 추후 나를 찜한 스토어 / 내가 찜한 스토어 정보를 다르게 보여줄 일이 생긴다면
  // 이를 분리하여 바꿀 수 있는 장점이
  // 코드가 중복된다는 단점보다 큰 것 같아 이렇게 선택
  private Integer bossSeq;
  private String storeName;
  private String storeLoc;
  private List<String> foodTypes;
  private List<String> tagInfos;
}
