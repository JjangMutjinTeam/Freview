package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.nuguna.freview.dto.common.ResponseMessage;
import java.io.PrintWriter;

public class JsonResponseUtil {

  public static <T> void sendBackJson(ResponseMessage<T> responseJsonSource,
      PrintWriter out,
      Gson gson) {
    String jsonStr = gson.toJson(responseJsonSource);
    out.print(jsonStr);
  }

}
