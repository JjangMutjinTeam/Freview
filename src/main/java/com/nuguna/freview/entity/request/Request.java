package com.nuguna.freview.entity.request;

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
public class Request {

  private int seq;
  private int fromMemberSeq;
  private int toMemberSeq;
  private Date comeDate;
  private boolean comeOrNot;
  private boolean reviewOrNot;
  private String gubun;
  private int fromPost;
  private String benefitDetail;
  private RequestStatus status;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  public String getStatus() {
    return status != null ? status.getStatus() : null;
  }

  public void setStatus(String status) {
    this.status = RequestStatus.fromString(status);
  }
}