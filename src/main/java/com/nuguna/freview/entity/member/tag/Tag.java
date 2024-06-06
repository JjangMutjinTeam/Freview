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

  private TagItem tagItem;

  public boolean isCustTag() {
    return tagItem.isCustTag();
  }

  public boolean isBossTag() {
    return tagItem.isCustTag();
  }

}
