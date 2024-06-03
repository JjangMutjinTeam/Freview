package com.nuguna.freview.entity.member.foodtype;

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

  private final String code;

  public static FoodTypeGubun from(String code) {
    for (FoodTypeGubun g : FoodTypeGubun.values()) {
      if (g.getCode().equalsIgnoreCase(code)) {
        return g;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 FoodTypeGubun 입력 : " + code);
  }
}