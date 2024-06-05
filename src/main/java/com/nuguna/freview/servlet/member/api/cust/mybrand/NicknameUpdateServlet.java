package com.nuguna.freview.servlet.member.api.cust.mybrand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nuguna.freview.dao.member.CustBrandDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/cust/my-brand/nickname")
public class NicknameUpdateServlet extends HttpServlet {

  private CustBrandDAO custBrandDAO;

  @Override
  public void init() throws ServletException {
    log.info("NicknameUpdateServlet 초기화");
    custBrandDAO = new CustBrandDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");

    log.info("NicknameUpdateServlet.doPost");

    Gson gson = new Gson();
    PrintWriter out = response.getWriter();
    ResponseMessage<String> responseJsonSource;
    String message = null;

    try {
      // JSON Parsing
      BufferedReader reader = request.getReader();
      StringBuilder jsonBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBuilder.append(line);
      }
      String jsonString = jsonBuilder.toString();
      JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      Integer memberSeq = jsonObject.get("member_seq").getAsInt();
      String fromNickname = jsonObject.get("from_nickname").getAsString();
      String toNickname = jsonObject.get("to_nickname").getAsString();

      // 입력값 검증
      boolean hasError = false;

      if (toNickname == null || toNickname.isEmpty()) {
        message = "비어있는 닉네임 값입니다.";
        hasError = true;
      }

      // 중복된 닉네임인지 확인
      boolean isExistNickname = custBrandDAO.checkNicknameIsExist(toNickname);
      if (isExistNickname) {
        message = "중복된 닉네임입니다. 다시 입력해주세요.";
        hasError = true;
      }

      // 기존과 동일한 닉네임
      if (fromNickname.equals(toNickname)) {
        message = "기존과 동일한 닉네임입니다.";
        hasError = true;
      }

      if (hasError) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseJsonSource = new ResponseMessage<>(message, null);
        String jsonStr = gson.toJson(responseJsonSource);
        out.print(jsonStr);
        // JsonUtil.print(ResponseMessage<T> jsonSource)
      } else {
        // 닉네임 업데이트
        custBrandDAO.updateNickname(memberSeq, toNickname);
        // 성공 응답
        response.setStatus(HttpServletResponse.SC_OK);
        responseJsonSource = new ResponseMessage<>("성공적으로 수정했습니다.", toNickname); // 화면 업데이트
        String jsonStr = gson.toJson(responseJsonSource);
        out.print(jsonStr);
      }
    } catch (NumberFormatException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "memberSeq는 null일 수 없습니다.");
    } catch (Exception e) {
      log.error("닉네임 변경 도중 에러가 발생했습니다.", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}