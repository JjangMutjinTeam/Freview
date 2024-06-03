package com.nuguna.freview.entity.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum PostGubun {
  MJ("MJ"), // 모집(MJ) 게시글
  GJ("GJ"); // 공지(GJ) 게시글

  private final String code;

  public static PostGubun from(String code) {
    for (PostGubun g : PostGubun.values()) {
      if (g.getCode().equalsIgnoreCase(code)) {
        return g;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 PostGubun 입력 : " + code);
  }
}