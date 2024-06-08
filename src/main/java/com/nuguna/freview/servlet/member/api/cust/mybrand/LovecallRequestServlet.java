package com.nuguna.freview.servlet.member.api.cust.mybrand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.CustLovecallDAO;
import com.nuguna.freview.dao.member.cust.MemberUtilDAO;
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
@WebServlet("/api/cust/my-brand/lovecall-request")
public class LovecallRequestServlet extends HttpServlet {

  private Gson gson;
  private MemberUtilDAO memberUtilDAO;
  private CustLovecallDAO custLovecallDAO;

  @Override
  public void init() throws ServletException {
    log.info("Cust - LovecallRequestServlet 초기화");
    gson = new Gson();
    memberUtilDAO = new MemberUtilDAO();
    custLovecallDAO = new CustLovecallDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("Cust - LovecallRequestServlet.doPost");


    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      // TODO : 서블릿 필터에서 memberSeq의 유효성을 체크해준다고 가정
      int bossSeq = jsonObject.get("member_seq").getAsInt();
      int custSeq = jsonObject.get("cust_seq").getAsInt();
      String benefitDetails = jsonObject.get("benefit_details").getAsString();

      if (!memberUtilDAO.selectMemberGubun(bossSeq).isBoss()
          || !memberUtilDAO.selectMemberGubun(custSeq).isCust()) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
            new ResponseMessage<>("잘못된 요청 ( 사용자가 사장님이 아니거나, 해당 브랜드 페이지의 주인이 체험단이 아닙니다.",
                benefitDetails), response, gson);
      } else {
        custLovecallDAO.submitLovecall(bossSeq, custSeq, benefitDetails);
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
            new ResponseMessage<>("성공적으로 체험단에게 러브콜을 보냈습니다.", benefitDetails), response, gson);
      }
    } catch (JsonParseException e) {
      log.error("러브콜 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("러브콜 입력 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("러브콜 입력 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }

}
