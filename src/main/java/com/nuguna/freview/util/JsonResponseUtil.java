package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.nuguna.freview.dto.common.ResponseMessage;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class JsonResponseUtil {

  public static <T> void sendBackJsonWithStatus(int statusCode,
      ResponseMessage<T> responseJsonSource,
      HttpServletResponse response,
      Gson gson) throws IOException {
    response.setStatus(statusCode);
    String jsonStr = gson.toJson(responseJsonSource);
    response.getWriter().print(jsonStr);
  }

  public static <T> void sendJsonResponse(Map<String, Object> responseMap, HttpServletResponse response, Gson gson)
      throws IOException {
    String jsonResponse = gson.toJson(responseMap);
    response.getWriter().write(jsonResponse);
  }
}
