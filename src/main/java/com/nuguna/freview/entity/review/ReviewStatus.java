package com.nuguna.freview.entity.review;

import lombok.Getter;

@Getter
public enum ReviewStatus {
  UNWRITTEN("UNWRITTEN"),
  WRITTEN("WRITTEN");

  private final String status;

  ReviewStatus(String status) {
    this.status = status;
  }

  public static ReviewStatus fromString(String status) {
    for (ReviewStatus rs : ReviewStatus.values()) {
      if (rs.getStatus().equalsIgnoreCase(status)) {
        return rs;
      }
    }
    throw new IllegalArgumentException("유효하지 않은 상태 입력 " + status);
  }
}