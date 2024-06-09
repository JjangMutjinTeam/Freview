package com.nuguna.freview.entity.member.foodtype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FoodType {

  private Integer foodTypeSeq;
  private FoodTypeGubun foodTypeGubun;

  public String getCode() {
    return foodTypeGubun != null ? foodTypeGubun.getCode() : null;
  }

  public String getName() {
    return foodTypeGubun != null ? foodTypeGubun.getName() : null;
  }
}