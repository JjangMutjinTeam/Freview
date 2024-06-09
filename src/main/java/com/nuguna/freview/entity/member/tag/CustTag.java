package com.nuguna.freview.entity.member.tag;

import com.nuguna.freview.exception.IllegalTagException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class CustTag extends TagItem {

  private static final List<String> custTagNames = new ArrayList<>(
      Arrays.asList(
          "초식",
          "육식",
          "빵빵이")
  );

  public CustTag(String tagName) {
    validateTagName(tagName);
    this.tagName = tagName;
    this.gubun = TagGubun.CUST;
  }

  @Override
  protected void validateTagName(String tagName) throws IllegalTagException {
    if (!custTagNames.contains(tagName)) {
      throw new IllegalTagException("유효하지 않은 CustTag 입력");
    }
  }
}
