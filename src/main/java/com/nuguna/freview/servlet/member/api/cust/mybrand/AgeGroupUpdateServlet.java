package com.nuguna.freview.servlet.member.api.cust.mybrand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nuguna.freview.dao.member.CustAgeGroupDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.exception.UnsupportedAgeGroupException;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonRequestUtil;
import com.nuguna.freview.util.JsonResponseUtil;
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
@WebServlet("/api/cust/my-brand/age-group")
public class AgeGroupUpdateServlet extends HttpServlet {

  private CustAgeGroupDAO custAgeGroupDAO;

  @Override
  public void init() throws ServletException {
    log.info("AgeGroupUpdateServlet 초기화");
    custAgeGroupDAO = new CustAgeGroupDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("NicknameUpdateServlet.doPost");

    BufferedReader in = request.getReader();
    PrintWriter out = response.getWriter();
    Gson gson = new Gson();
    String message = null;

    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(in, gson);
      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      int memberSeq = jsonObject.get("member_seq").getAsInt();
      String toAgeGroup = jsonObject.get("to_age_group").getAsString();

      custAgeGroupDAO.updateAgeGroup(memberSeq, toAgeGroup);

      response.setStatus(HttpServletResponse.SC_OK);
      JsonResponseUtil.sendBackJson(new ResponseMessage<>("성공적으로 수정되었습니다.", toAgeGroup), out, gson);
    } catch (UnsupportedAgeGroupException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      JsonResponseUtil.sendBackJson(new ResponseMessage<>("유효하지 않은 연령대입니다.", null), out,
          gson);
    } catch (Exception e) {
      log.error("연령대 변경 도중 에러가 발생했습니다.", e);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      JsonResponseUtil.sendBackJson(new ResponseMessage<>("연령대 변경 도중 서버 에러가 발생했습니다.", null), out,
          gson);
    }
  }
}
