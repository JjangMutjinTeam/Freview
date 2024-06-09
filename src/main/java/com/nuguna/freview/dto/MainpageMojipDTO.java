package com.nuguna.freview.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainpageMojipDTO {
  private String thumbnailPhotoUrl;
  private Integer memberSeq;
  private String title;
  private Date applyStartDate;
  private Date applyEndDate;
  private Date experienceDate;
  private Integer viewCount;
}
