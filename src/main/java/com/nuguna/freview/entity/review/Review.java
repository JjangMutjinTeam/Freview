package com.nuguna.freview.entity.review;

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
public class Review {

  private Integer reviewSeq;
  private Integer custSeq;
  private Integer bossSeq;
  private String url;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private ReviewStatus status;

  public String getStatus() {
    return status != null ? status.getStatus() : null;
  }

  public void setStatus(String status) {
    this.status = ReviewStatus.from(status);
  }
}