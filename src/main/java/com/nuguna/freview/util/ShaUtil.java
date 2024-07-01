package com.nuguna.freview.util;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class ShaUtil {
  public String sha256Encodeing(String purePw){
    return Hashing.sha256().hashString(purePw, StandardCharsets.UTF_8).toString();
  }
}
