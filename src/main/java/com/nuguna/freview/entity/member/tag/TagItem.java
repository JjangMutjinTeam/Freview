package com.nuguna.freview.entity.member.tag;

import com.nuguna.freview.exception.IllegalTagException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class TagItem {

  protected TagGubun gubun;
  protected String tagName;

  protected abstract void validateTagName(String tagName) throws IllegalTagException;

  public boolean isCustTag() {
    return gubun.isCustTag();
  }

  public boolean isBossTag() {
    return gubun.isBossTag();
  }
}