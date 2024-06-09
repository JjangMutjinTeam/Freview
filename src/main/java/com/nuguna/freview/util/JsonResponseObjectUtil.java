package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.nuguna.freview.dto.api.boss.BossResponseData;
import com.nuguna.freview.dto.common.ResponseMessage;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

public class JsonResponseObjectUtil extends JsonResponseUtil {

  public static <T> void sendBackJsonWithStatusAndMap(int statusCode,
      BossResponseData<String, Object> responseData,
      HttpServletResponse response,
      Gson gson) throws IOException {
    response.setStatus(statusCode);
    String jsonStr = gson.toJson(responseData);
    response.getWriter().print(jsonStr);
  }
}