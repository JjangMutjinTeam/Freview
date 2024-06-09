package com.nuguna.freview.util;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

public class JsonResponseObjectUtil extends JsonResponseUtil {

  public static HashMap<Object, Object> sendBackJsonWithStatusAndMap(int statusCode,
      HashMap<Object, Object> responseData,
      HttpServletResponse response,
      Gson gson) throws IOException {
    response.setStatus(statusCode);
    String jsonStr = gson.toJson(responseData);
    response.getWriter().print(jsonStr);
    return responseData;
  }
}