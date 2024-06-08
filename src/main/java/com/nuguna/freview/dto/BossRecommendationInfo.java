package com.nuguna.freview.dto;

import com.nuguna.freview.entity.member.foodtype.FoodTypeGubun;
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
public class BossRecommendationInfo {

  private String nickname;
  private String profilePhotoUrl;
  private String foodTypes;
  private String tags;

}
