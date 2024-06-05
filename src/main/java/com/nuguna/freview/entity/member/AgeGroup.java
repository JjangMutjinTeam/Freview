package com.nuguna.freview.entity.member;

import com.nuguna.freview.exception.UnsupportedAgeGroupException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum AgeGroup {
  TEENS("10대"),
  TWENTIES("20대"),
  THIRTIES("30대"),
  FORTIES("40대"),
  FIFTIES("50대"),
  SIXTIES("60대"),
  SEVENTIES("70대"),
  EIGHTIES("80대"),
  NINETIES("90대");

  private final String ageGroup;

  public static AgeGroup from(String ageGroup) {
    for (AgeGroup a : AgeGroup.values()) {
      if (a.getAgeGroup().equalsIgnoreCase(ageGroup)) {
        return a;
      }
    }
    throw new UnsupportedAgeGroupException("유효하지 않은 AgeGroup 입력 : " + ageGroup);
  }

}
