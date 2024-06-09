package com.nuguna.freview.entity.member.foodtype;

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
public class FoodType {

  private Integer foodTypeSeq;
  private FoodTypeGubun codeName;

  public String getCodeName() {
    return codeName != null ? codeName.getCodeName() : null;
  }

  public void setName(String name) {
    this.codeName = FoodTypeGubun.from(name);
  }
}