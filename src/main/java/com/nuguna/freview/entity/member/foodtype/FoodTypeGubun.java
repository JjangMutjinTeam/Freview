package com.nuguna.freview.entity.member.foodtype;

import com.nuguna.freview.exception.UnsupportedFoodTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum FoodTypeGubun {
  KF("KF"),
  WF("WF"),
  CF("CF"),
  JF("JF"),
  BC("BC"),
  ETC("ETC");

  private final String codeName;

  public static FoodTypeGubun from(String foodTypeName) {
    for (FoodTypeGubun g : FoodTypeGubun.values()) {
      if (g.getCodeName().equalsIgnoreCase(foodTypeName)) {
        return g;
      }
    }
    throw new UnsupportedFoodTypeException("유효하지 않은 FoodTypeGubun 입력 : " + foodTypeName);
  }
}