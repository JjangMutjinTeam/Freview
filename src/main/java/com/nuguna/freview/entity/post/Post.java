package com.nuguna.freview.entity.post;

import java.sql.Date;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  private int postSeq;
  private int memberSeq;
  private String title;
  private Date applyStartDate;
  private Date applyEndDate;
  private Date experienceDate;
  private String content;
  private String gubun;
  private int viewCount;
  private String thumbnailPhotoUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;
}