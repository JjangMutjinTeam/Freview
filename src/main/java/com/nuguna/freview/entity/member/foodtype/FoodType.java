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
  private FoodTypeGubun name;

  public String getName() {
    return name != null ? name.getCode() : null;
  }

  public void setName(String name) {
    this.name = FoodTypeGubun.from(name);
  }
}