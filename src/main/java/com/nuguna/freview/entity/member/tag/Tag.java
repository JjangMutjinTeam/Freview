package com.nuguna.freview.entity.member.tag;

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
public class Tag {

  private Integer tagSeq;
  private TagGubun gubun;
  private String name;

  public String getGubun() {
    return gubun != null ? gubun.getCode() : null;
  }

  public void setGubun(String gubun) {
    this.gubun = TagGubun.from(gubun);
  }
}