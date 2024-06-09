package com.nuguna.freview.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum MemberGubun {
  ADMIN("A"),
  BOSS("B"),
  CUST("C");

  private final String code;

  public static MemberGubun from(String code) {
    for (MemberGubun g : MemberGubun.values()) {
      if (g.getCode().equalsIgnoreCase(code)) {
        return g;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 MemberGubun 입력 : " + code);
  }
  
  public boolean isAdmin() {
    return this == ADMIN;
  }

  public boolean isBoss() {
    return this == BOSS;
  }

  public boolean isCust() {
    return this == CUST;
  }
}
