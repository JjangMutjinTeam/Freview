package com.nuguna.freview.servlet.member.page;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossSendInformDAO;
import com.nuguna.freview.dto.api.boss.BossSendDdabongDto;
import com.nuguna.freview.dto.api.boss.BossSendZzimInfoDto;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonRequestUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.util.List;
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
    System.out.println("doGet 진입");
    EncodingUtil.setEncodingToUTF8AndJson(request, response);
    try {
//      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
//      int bossSeq = jsonObject.get("member_seq").getAsInt();
      int bossSeq = 118;

      List<BossSendZzimInfoDto> zzimSendInfos = BossSendInformDAO.sendZzimDAO(bossSeq);
      List<BossSendDdabongDto> ddabongSendInfos = BossSendInformDAO.sendDdabongDAO(bossSeq);

      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("내가 찜한 게시글의 정보를 성공적으로 가져왔습니다.", zzimSendInfos), response, gson);

      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("내가 따봉한 게시글의 정보를 성공적으로 가져왔습니다.", ddabongSendInfos), response, gson);

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
