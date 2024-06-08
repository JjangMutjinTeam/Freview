package com.nuguna.freview.servlet.member.api.cust.mybrand;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.cust.CustTagDAO;
import com.nuguna.freview.dao.member.cust.MemberCustDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.entity.member.MemberGubun;
import com.nuguna.freview.exception.IllegalTagException;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonRequestUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/cust/my-brand/tag")
public class TagUpdateServlet extends HttpServlet {

  private Gson gson;
  private MemberCustDAO memberCustDAO;
  private CustTagDAO custTagDAO;

  @Override
  public void init() throws ServletException {
    log.info("TagUpdateServlet 초기화");
    gson = new Gson();
    memberCustDAO = new MemberCustDAO();
    custTagDAO = new CustTagDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    log.info("TagUpdateServlet.doPost");

    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      // 입력값 가져오기 ( 클라이언트에서 데이터를 올바르게 주는 경우만 가정 )
      // TODO : 추후 Input Data가 NULL 인 경우 또한 처리해주어야 함.
      // TODO : 서블릿 필터에서 memberSeq의 유효성을 체크해준다고 가정
      int memberSeq = jsonObject.get("member_seq").getAsInt();
      JsonArray tags = jsonObject.get("to_tags").getAsJsonArray();

      List<String> tagNames = tags.asList().stream()
          .map(JsonElement::getAsString)
          .collect(Collectors.toList());

      MemberGubun memberGubun = memberCustDAO.selectMemberGubun(memberSeq);

      if (!memberGubun.isCust()) {
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("해당 유저의 구분은 체험단이 아닙니다.", tagNames), response, gson);
      } else {
        custTagDAO.updateTags(memberSeq, tagNames);

        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
            new ResponseMessage<>("성공적으로 수정되었습니다.", tagNames), response, gson);
      }
    } catch (JsonParseException e) {
      log.error("태그 변경 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (IllegalTagException e) {
      log.error("유효하지 않은 체험단용 태그입니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("유효하지 않은 체험단용 태그입니다.", null), response, gson);
    } catch (Exception e) {
      log.error("태그 변경 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("태그 변경 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }

}
