package com.nuguna.freview.servlet.member.page;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossSendInformDAO;
import com.nuguna.freview.dto.api.boss.BossSendLikesDto;
import com.nuguna.freview.dto.api.boss.BossSendZzimInfoDto;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/boss/my-notification/send-inform")
public class SendInformServlet extends HttpServlet {

  private Gson gson;
  private BossSendInformDAO BossSendInformDAO;

  public void init(ServletConfig config) throws ServletException {
    gson = new Gson();
    BossSendInformDAO = new BossSendInformDAO();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    loginUser = (Member) session.getAttribute("Member");
    memberSeq = loginUser.getMemberSeq();
    EncodingUtil.setEncodingToUTF8AndJson(request, response);
    try {
//      int bossSeq = 118;
//      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
//      int bossSeq = jsonObject.get("member_seq").getAsInt();

      List<BossSendZzimInfoDto> zzimSendInfos = BossSendInformDAO.sendZzimDAO(bossSeq);
      List<BossSendLikesDto> ddabongSendInfos = BossSendInformDAO.sendDdabongDAO(bossSeq);

//      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
//          new ResponseMessage<>("내가 찜한 게시글의 정보를 성공적으로 가져왔습니다.", zzimSendInfos), response, gson);
//      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
//          new ResponseMessage<>("내가 따봉한 게시글의 정보를 성공적으로 가져왔습니다.", ddabongSendInfos), response, gson);

      System.out.println("zzimSendInfos:" + zzimSendInfos);
      System.out.println("ddabongSendInfos:" + ddabongSendInfos);
      // JSON으로 응답을 생성
      PrintWriter out = response.getWriter();

      Map<String, Object> responseData = new HashMap<>();
      responseData.put("zzimInfos", zzimSendInfos);
      responseData.put("ddabongInfos", ddabongSendInfos);

      out.println(gson.toJson(responseData));
      out.flush();
      System.out.println("얘도 오케이");


    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (JsonParseException e) {
      log.error("찜 정보 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("찜 정보 요청 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("찜 정보 요청 도중 서버 에러가 발생했습니다.", null), response, gson);
    }

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


  }


}
