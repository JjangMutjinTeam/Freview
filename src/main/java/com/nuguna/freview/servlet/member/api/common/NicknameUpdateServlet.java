package com.nuguna.freview.servlet.member.api.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.common.MemberBrandInfoDAO;
import com.nuguna.freview.dao.member.common.MemberUtilDAO;
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
@WebServlet("/api/my-brand/nickname")
public class NicknameUpdateServlet extends HttpServlet {

  private Gson gson;
  private MemberUtilDAO memberUtilDAO;
  private MemberBrandInfoDAO memberBrandInfoDAO;

  @Override
  public void init() throws ServletException {
    log.info("NicknameUpdateServlet 초기화");
    gson = new Gson();
    memberUtilDAO = new MemberUtilDAO();
    memberBrandInfoDAO = new MemberBrandInfoDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("NicknameUpdateServlet.doPost");

    String errorMessage = null;

    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      // TODO : 서블릿 필터에서 memberSeq의 유효성을 체크해준다고 가정
      int memberSeq = jsonObject.get("member_seq").getAsInt();
      String fromNickname = jsonObject.get("from_nickname").getAsString();
      String toNickname = jsonObject.get("to_nickname").getAsString();

      if (!memberUtilDAO.isValidMember(memberSeq)) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("주어진 member_seq에 해당하는 멤버가 존재하지 않습니다.", null), response, gson);
        return;
      }

      if (toNickname == null || toNickname.isEmpty()) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("비어있는 닉네임 값입니다.", null), response, gson);
        return;
      }

      // 중복된 닉네임인지 확인
      boolean isExistNickname = memberBrandInfoDAO.checkNicknameIsExist(toNickname);
      if (isExistNickname) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("중복된 닉네임입니다. 다시 입력해주세요.", null), response, gson);
        return;
      }

      // 기존과 동일한 닉네임
      if (fromNickname.equals(toNickname)) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("기존과 동일한 닉네임입니다.", null), response, gson);
        return;
      }

      memberBrandInfoDAO.updateNickname(memberSeq, toNickname);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("성공적으로 수정했습니다.", toNickname), response, gson);
    } catch (JsonParseException e) {
      log.error("닉네임 변경 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("닉네임 변경 도중 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("닉네임 변경 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }
}