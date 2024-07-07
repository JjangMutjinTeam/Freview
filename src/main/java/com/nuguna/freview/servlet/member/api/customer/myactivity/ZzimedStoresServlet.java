package com.nuguna.freview.servlet.member.api.customer.myactivity;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.cust.page.CustMyActivityDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.dto.cust.brand.CustMyZzimStoreDto;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.exception.IllegalAgeGroupException;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/customer/my-activity/zzim-stores")
public class ZzimedStoresServlet extends HttpServlet {

  private Gson gson;
  private CustMyActivityDAO myActivityDAO;

  @Override
  public void init() throws ServletException {
    gson = new Gson();
    myActivityDAO = new CustMyActivityDAO();
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("Cust - AgeGroupUpdateServlet.doPost");

    try {
      // 세션 확인
      Member member = (Member) request.getSession().getAttribute("Member");
      int memberSeq = member.getMemberSeq();

      log.info("ZzimedStoresServlet 들어옴");

      /*
      페이지네이션 도입 시 사용
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      String toAgeGroup = jsonObject.get("page_count").getAsString();
      */
      List<CustMyZzimStoreDto> zzimStores = myActivityDAO.getZzimStores(memberSeq);
      log.info("zzimStores.size() : {}", zzimStores.size());
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("내가 찜한 스토어의 정보를 가져왔습니다.", zzimStores), response, gson);
    } catch (JsonParseException e) {
      log.error("연령대 변경 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (IllegalAgeGroupException e) {
      log.error("유효하지 않은 연령대 요청입니다.");
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("유효하지 않은 연령대입니다.", null), response, gson);
    } catch (Exception e) {
      log.error("연령대 변경 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("연령대 변경 도중 서버 에러가 발생했습니다.", null), response, gson);
    }

  }

  @Override
  public void destroy() {
  }
}
