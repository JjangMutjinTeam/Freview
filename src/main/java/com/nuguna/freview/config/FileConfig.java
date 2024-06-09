package com.nuguna.freview.config;

public class FileConfig {

  // 유저에 따라 변경해주어야 함.
  public static final String BASE_UPLOAD_DIR = "/Users/choi/Desktop/fileUpload";
  public static final String CUST_PROFILE_PHOTO_DIR = BASE_UPLOAD_DIR + "/cust";
  public static final String BOSS_PROFILE_PHOTO_DIR = BASE_UPLOAD_DIR + "/boss";
  public static final String ADMIN_PROFILE_PHOTO_DIR = BASE_UPLOAD_DIR + "/admin";
  public static final int FILE_MAX_SIZE = 1024 * 1024 * 50; // 50MB
  public static final int FILE_SIZE_THRESHOLD = 1024 * 1024; // 1MB

}
