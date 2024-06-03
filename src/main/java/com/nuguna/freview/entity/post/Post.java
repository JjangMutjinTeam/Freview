package com.nuguna.freview.entity.post;

import java.sql.Date;
import java.sql.Timestamp;
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
public class Post {

  private Integer postSeq;
  private Integer memberSeq;
  private String title;
  private Date applyStartDate;
  private Date applyEndDate;
  private Date experienceDate;
  private String content;
  private PostGubun gubun;
  private Integer viewCount;
  private String thumbnailPhotoUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  public String getGubun() {
    return gubun != null ? gubun.getCode() : null;
  }

  public void setGubun(String gubun) {
    this.gubun = PostGubun.from(gubun);
  }
}