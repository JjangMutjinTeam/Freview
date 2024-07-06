package com.nuguna.freview.dto.cust.activitylog;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustMyLikePostDto {

  private Integer seq; // 게시물 seq
  private String title;
  private String content;
  private Integer likesCount;
  private Date createdAt;
}
