package com.nuguna.freview.entity.request;

import lombok.Getter;

@Getter
public enum RequestStatus {
  PENDING("PENDING"),
  AGREED("AGREED"),
  COME("COME"),
  NOSHOW("NOSHOW"),
  REJECTED("REJECTED");

  private final String status;

  RequestStatus(String status) {
    this.status = status;
  }

  public static RequestStatus fromString(String status) {
    for (RequestStatus rs : RequestStatus.values()) {
      if (rs.getStatus().equalsIgnoreCase(status)) {
        return rs;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 상태 입력 " + status);
  }
}