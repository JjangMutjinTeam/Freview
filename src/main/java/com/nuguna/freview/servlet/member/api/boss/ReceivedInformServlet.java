package com.nuguna.freview.servlet.member.api.boss;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossReceivedInformDAO;
import com.nuguna.freview.dto.api.boss.BossReceivedLikesDto;
import com.nuguna.freview.dto.api.boss.BossReceivedZzimInfoDto;
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
@WebServlet("/api/boss/received-inform")
public class ReceivedInformServlet extends HttpServlet  {

  private Gson gson;
  private BossReceivedInformDAO BossReceivedInformDAO;

  public void init() throws ServletException {
    gson = new Gson();
    BossReceivedInformDAO = new BossReceivedInformDAO();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    EncodingUtil.setEncodingToUTF8AndJson(request, response);

    try {
      int memberSeq = loginUser.getMemberSeq();  // session에서 memberSeq 추출
      System.out.println("ReceivedInform 진입 = " + memberSeq);

      List<BossReceivedZzimInfoDto> zzimInfos = BossReceivedInformDAO.receivedZzimDAO(memberSeq);
      List<BossReceivedLikesDto> likesInfos = BossReceivedInformDAO.receivedLikeDAO(memberSeq);

      Map<String, Object> responseData = new HashMap<>();
      responseData.put("zzimInfos", zzimInfos);
      responseData.put("likesInfos", likesInfos);

      PrintWriter out = response.getWriter();
      out.println(gson.toJson(responseData));
      out.flush();

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (JsonParseException e) {
      log.error("정보 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("정보 요청 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("정보 요청 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }

}
