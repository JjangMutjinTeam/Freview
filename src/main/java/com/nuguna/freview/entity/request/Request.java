package com.nuguna.freview.entity.request;

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
public class Request {

  private Integer seq;
  private Integer fromMemberSeq;
  private Integer toMemberSeq;
  private Date comeDate;
  private Boolean comeOrNot;
  private Boolean reviewOrNot;
  private RequestGubun gubun;
  private Integer fromPost;
  private String benefitDetail;
  private RequestStatus status;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  public String getGubun() {
    return gubun != null ? gubun.getCode() : null;
  }

  public void setGubun(String gubun) {
    this.gubun = RequestGubun.from(gubun);
  }

  public String getStatus() {
    return status != null ? status.getStatus() : null;
  }

  public void setStatus(String status) {
    this.status = RequestStatus.from(status);
  }
}