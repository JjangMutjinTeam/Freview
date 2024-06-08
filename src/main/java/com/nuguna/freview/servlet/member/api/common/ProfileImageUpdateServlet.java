package com.nuguna.freview.servlet.member.api.common;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.config.FileConfig;
import com.nuguna.freview.dao.member.ProfileImageDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MultipartConfig(
    fileSizeThreshold = FileConfig.FILE_SIZE_THRESHOLD,
    maxFileSize = FileConfig.FILE_MAX_SIZE
)
@WebServlet("/api/cust/my-brand/profile")
public class ProfileImageUpdateServlet extends HttpServlet {

  private Gson gson;
  private ProfileImageDAO profileImageDAO;

  @Override
  public void init() throws ServletException {
    log.info("ProfileImageUpdateServlet 초기화");
    gson = new Gson();
    profileImageDAO = new ProfileImageDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("ProfileImageUpdateServlet.doPost");

    try {
      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      // TODO : 서블릿 필터에서 memberSeq의 유효성을 체크해준다고 가정
      int memberSeq = Integer.parseInt(request.getParameter("member_seq"));
      // 파일 가져오기
      Part filePart = request.getPart("profile_file");

      log.info("filePart = " + filePart);

      String filePath;
      // 비어있는 프로필 파일을 전송하면
      if (filePart == null || filePart.getSubmittedFileName().isEmpty()) {
        filePath = "";
      } else {
        // 파일 경로 지정
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        filePath = FileConfig.UPLOAD_DIR + File.separator + fileName;
        // 파일 저장
        File uploadFile = new File(filePath);
        try (InputStream fileContent = filePart.getInputStream()) {
          Files.copy(fileContent, uploadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
      }
      // 1. 프로필 사진을 기본 프로필로 변경하는 경우
      // 2. 프로필 사진을 업로드/업데이트 하는 경우
      log.info("filePath = " + filePath);
      profileImageDAO.updateMemberProfile(memberSeq, filePath);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("성공적으로 프로필을 수정했습니다.", null), response, gson);
    } catch (JsonParseException e) {
      log.error("프로필 사진 수정 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("프로필 사진 수정 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("프로필 사진 수정 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }

}
