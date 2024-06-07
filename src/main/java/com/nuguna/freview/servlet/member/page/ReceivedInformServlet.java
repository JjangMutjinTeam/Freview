package com.nuguna.freview.servlet.member.page;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossReceviedInformDAO;
import com.nuguna.freview.dto.api.boss.BossReceivedZzimInfoDto;
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
@WebServlet("/api/boss/my-notification/recevied-inform")
public class ReceivedInformServlet extends HttpServlet  {

  private Gson gson;
  private BossReceviedInformDAO BossReceviedInformDAO;

  public void init() throws ServletException {
    gson = new Gson();
    BossReceviedInformDAO = new BossReceviedInformDAO();

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);
    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      int bossSeq = jsonObject.get("member_seq").getAsInt();

      List<BossReceivedZzimInfoDto> zzimInfos = BossReceviedInformDAO.receivedZzimDAO(
          bossSeq);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("나를 찜한 유저들의 정보를 성공적으로 가져왔습니다.", zzimInfos), response, gson);
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

}
