package com.nuguna.freview.entity.member.foodtype;

import com.nuguna.freview.exception.IllegalFoodTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum FoodTypeGubun {
  KF("KF", "한식"),
  WF("WF", "양식"),
  CF("CF", "중식"),
  JF("JF", "일식"),
  BC("BC", "빵&베이커리"),
  ETC("ETC", "기타");

  private final String code;
  private final String name;

  public static FoodTypeGubun fromCode(String code) {
    for (FoodTypeGubun g : FoodTypeGubun.values()) {
      if (g.getCode().equalsIgnoreCase(code)) {
        return g;
      }
    }
    throw new IllegalFoodTypeException("유효하지 않은 FoodType Code 입력 : " + code);
  }

  public static FoodTypeGubun fromName(String name) {
    for (FoodTypeGubun g : FoodTypeGubun.values()) {
      if (g.getName().equalsIgnoreCase(name)) {
        return g;
      }
    }
    throw new IllegalFoodTypeException("유효하지 않은 FoodType Name 입력 : " + name);
  }
}