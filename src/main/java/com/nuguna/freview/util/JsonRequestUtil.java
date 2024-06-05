package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonRequestUtil {

  public static JsonObject parseJson(HttpServletRequest request, HttpServletResponse response,
      Gson gson)
      throws IOException {
    PrintWriter writer = response.getWriter();
    // JSON Parsing
    BufferedReader reader = request.getReader();
    StringBuilder jsonBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      jsonBuilder.append(line);
    }
    String jsonString = jsonBuilder.toString();
    return gson.fromJson(jsonString, JsonObject.class);
  }

}
