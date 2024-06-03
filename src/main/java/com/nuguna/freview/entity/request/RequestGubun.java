package com.nuguna.freview.entity.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum RequestGubun {
  LC("LC"), // 러브콜
  AP("AP"); // 지원 ( Apply )

  private final String code;

  public static RequestGubun from(String code) {
    for (RequestGubun g : RequestGubun.values()) {
      if (g.getCode().equalsIgnoreCase(code)) {
        return g;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 RequestGubun 입력 : " + code);
  }
}