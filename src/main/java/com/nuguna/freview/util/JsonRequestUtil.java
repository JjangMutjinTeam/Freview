package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonRequestUtil {

  public static JsonObject parseJson(BufferedReader in, Gson gson)
      throws IOException {
    StringBuilder jsonBuilder = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null) {
      jsonBuilder.append(line);
    }
    String jsonString = jsonBuilder.toString();
    return gson.fromJson(jsonString, JsonObject.class);
  }
}