package com.nuguna.freview.entity.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum RequestStatus {
  PENDING("PENDING"),
  AGREED("AGREED"),
  COME("COME"),
  NOSHOW("NOSHOW"),
  REJECTED("REJECTED");

  private final String status;

  public static RequestStatus from(String status) {
    for (RequestStatus rs : RequestStatus.values()) {
      if (rs.getStatus().equalsIgnoreCase(status)) {
        return rs;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 RequestStatus 입력 : " + status);
  }
}