package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.nuguna.freview.dto.common.ResponseMessage;
import java.io.IOException;
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

}
