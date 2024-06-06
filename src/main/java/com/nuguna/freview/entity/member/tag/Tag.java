package com.nuguna.freview.entity.member.tag;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Tag {

  protected Integer tagSeq;
  protected TagGubun gubun;
  protected String tagName;

  protected abstract void validateTagName(String tagName);

  public String getGubun() {
    return gubun != null ? gubun.getGubun() : null;
  }

  public void setGubun(String gubun) {
    this.gubun = TagGubun.from(gubun);
  }
}