package com.nuguna.freview.servlet.member.page;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossReceivedInformDAO;
import com.nuguna.freview.dto.api.boss.BossReceivedLikesDto;
import com.nuguna.freview.dto.api.boss.BossReceivedZzimInfoDto;
import com.nuguna.freview.dto.common.ResponseMessage;
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
@WebServlet("/api/boss/my-notification/received-inform")
public class ReceivedInformServlet extends HttpServlet  {

  private Gson gson;
  private BossReceivedInformDAO BossReceivedInformDAO;

  public void init() throws ServletException {
    gson = new Gson();
    BossReceivedInformDAO = new BossReceivedInformDAO();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);
    try {
//      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
//      int bossSeq = jsonObject.get("member_seq").getAsInt();
//      System.out.println("bossSeq: " + bossSeq);
//      System.out.println("bossSeq: "+request.getParameter("member_seq"));

//      int bossSeq = Integer.parseInt(request.getParameter("member_Seq"));
//      int bossSeq = 118;

      List<BossReceivedZzimInfoDto> zzimInfos = BossReceivedInformDAO.receivedZzimDAO(bossSeq);
      List<BossReceivedLikesDto> ddabongInfos = BossReceivedInformDAO.receivedDdabongDAO(bossSeq);

      // JSON으로 응답을 생성
      PrintWriter out = response.getWriter();

      Map<String, Object> responseData = new HashMap<>();
      responseData.put("zzimInfos", zzimInfos);
      responseData.put("ddabongInfos", ddabongInfos);

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
