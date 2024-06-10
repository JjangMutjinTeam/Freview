package com.nuguna.freview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainpageRequesterDTO {
  private int memberSeq;
  private String nickname;
  private String profilePhotoUrl;
}
