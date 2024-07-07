package com.nuguna.freview.dto;

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
public class AdminPersonalInfoDTO {

  private Integer memberSeq;
  private String id;
  private String pw;
  private String nickname;
  private String email;
}
