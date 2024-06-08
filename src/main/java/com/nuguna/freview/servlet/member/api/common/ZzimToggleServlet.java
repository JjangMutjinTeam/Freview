package com.nuguna.freview.servlet.member.api.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.common.MemberUtilDAO;
import com.nuguna.freview.dao.zzim.ZzimDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonRequestUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/my-brand/zzim")
public class ZzimToggleServlet extends HttpServlet {

  private Gson gson;
  private MemberUtilDAO memberUtilDAO;
  private ZzimDAO zzimDAO;

  @Override
  public void init() throws ServletException {
    log.info("ZzimToggleServlet 초기화");
    gson = new Gson();
    memberUtilDAO = new MemberUtilDAO();
    zzimDAO = new ZzimDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("ZzimToggleServlet.doPost");

    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      // TODO : 서블릿 필터에서 memberSeq의 유효성을 체크해준다고 가정
      int fromMemberSeq = jsonObject.get("from_member_seq").getAsInt();
      int toMemberSeq = jsonObject.get("to_member_seq").getAsInt();
      if (!memberUtilDAO.isValidMember(fromMemberSeq) || !memberUtilDAO.isValidMember(
          toMemberSeq)) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("두 멤버들 중 DB에 등록되지 않은 유저가 있습니다.", null), response, gson);
        return;
      }
      zzimDAO.toggleZzim(fromMemberSeq, toMemberSeq);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("성공적으로 찜 요청을 보냈습니다.", null), response, gson);
    } catch (JsonParseException e) {
      log.error("찜 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("찜 입력 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("찜 입력 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }
}
