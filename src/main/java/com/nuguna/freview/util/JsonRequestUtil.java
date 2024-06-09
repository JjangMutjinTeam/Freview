package com.nuguna.freview.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonRequestUtil {

  public static JsonObject parseJson(BufferedReader in, Gson gson)
      throws IOException, JsonParseException {
    StringBuilder jsonBuilder = new StringBuilder();
    String line;
    while ((line = in.readLine()) != null) {
      jsonBuilder.append(line);
    }
    String jsonString = jsonBuilder.toString();
    log.info("jsonString = " + jsonString);
    return gson.fromJson(jsonString, JsonObject.class);
  }
}
