package com.nuguna.freview.entity.member.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class CustTag extends Tag {

  private static final List<String> custTagNames = new ArrayList<>(
      Arrays.asList(
          "초식",
          "육식",
          "빵빵이")
  );

  public CustTag(Integer tagSeq, String tagName) {
    validateTagName(tagName);
    this.tagSeq = tagSeq;
    this.tagName = tagName;
    this.gubun = TagGubun.C;
  }

  @Override
  protected void validateTagName(String tagName) {
    if (!custTagNames.contains(tagName)) {
      throw new IllegalArgumentException("유효하지 않은 CustTag 입력");
    }
  }
}
