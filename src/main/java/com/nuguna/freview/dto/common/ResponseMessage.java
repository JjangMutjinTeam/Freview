package com.nuguna.freview.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage<T> {
  
  public String message;
  public T item;

}
