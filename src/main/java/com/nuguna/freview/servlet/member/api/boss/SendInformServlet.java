package com.nuguna.freview.servlet.member.api.boss;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.boss.BossSendInformDAO;
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
@WebServlet("/api/boss/send-inform")
public class SendInformServlet extends HttpServlet {

  private Gson gson;
  private BossSendInformDAO BossSendInformDAO;

  public void init(ServletConfig config) throws ServletException {
    gson = new Gson();
    BossSendInformDAO = new BossSendInformDAO();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    try {
      int memberSeq = loginUser.getMemberSeq();
      System.out.println("userSeq = " + memberSeq);

      List<BossSendZzimInfoDto> zzimSendInfos = BossSendInformDAO.sendZzimDAO(memberSeq);
      List<BossSendLikesDto> likesSendInfos = BossSendInformDAO.sendDdabongDAO(memberSeq);

      System.out.println("zzimSendInfos:" + zzimSendInfos);
      System.out.println("likesSendInfos:" + likesSendInfos);

      PrintWriter out = response.getWriter();
      Map<String, Object> responseData = new HashMap<>();
      responseData.put("zzimInfos", zzimSendInfos);
      responseData.put("likesInfos", likesSendInfos);

      out.println(gson.toJson(responseData));
      out.flush();

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
