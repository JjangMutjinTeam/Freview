package com.nuguna.freview.entity.member.tag;

import com.nuguna.freview.exception.IllegalTagException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class BossTag extends Tag {

  private static final List<String> bossTagNames = new ArrayList<>(
      Arrays.asList(
          "단체석",
          "뷰 맛집",
          "오션뷰",
          "반려동물 환영")
  );

  public BossTag(Integer tagSeq, String tagName) {
    validateTagName(tagName);
    this.tagSeq = tagSeq;
    this.tagName = tagName;
    this.gubun = TagGubun.B;
  }

  @Override
  protected void validateTagName(String tagName) throws IllegalTagException {
    if (!bossTagNames.contains(tagName)) {
      throw new IllegalTagException("유효하지 않은 BossTag 입력");
    }
  }
}
