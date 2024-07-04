package com.nuguna.freview.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingUtil {

  public static void setEncodingToUTF8AndJson(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");
  }

  public static void setEncodingToUTF8AndText(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
  }

  public static void setEncodingToUTF8AndUTF8(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
  }
}
