package com.nuguna.freview.dto;

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
public class StoreAndBossDTO {

  private String storeName;
  private String businessNumber;
  private String id;
  private Timestamp createdAt;

}
