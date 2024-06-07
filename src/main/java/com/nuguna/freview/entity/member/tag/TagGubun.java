package com.nuguna.freview.entity.member.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum TagGubun {
  BOSS("B"),
  CUST("C");

  private final String gubun;

  public static TagGubun from(String gubun) {
    for (TagGubun g : TagGubun.values()) {
      if (g.getGubun().equalsIgnoreCase(gubun)) {
        return g;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 TagGubun 입력 : " + gubun);
  }

  public boolean isBossTag() {
    return this == BOSS;
  }

  public boolean isCustTag() {
    return this == CUST;
  }

}