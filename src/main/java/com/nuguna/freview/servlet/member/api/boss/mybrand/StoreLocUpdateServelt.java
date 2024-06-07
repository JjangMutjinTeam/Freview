package com.nuguna.freview.servlet.member.api.boss.mybrand;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossStoreLocDAO;
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
@WebServlet("/api/boss/my-brand/store-loc")
public class StoreLocUpdateServelt extends HttpServlet {

  private Gson gson;
  private BossStoreLocDAO bossStoreLocDAO;

  @Override
  public void init() throws ServletException {
//    super.init();
    gson = new Gson();
    bossStoreLocDAO = new BossStoreLocDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    EncodingUtil.setEncodingToUTF8AndJson(request, response);
    try {
      JsonObject jsonObject = JsonRequestUtil.parseJson(request.getReader(), gson);
      int memberSeq = jsonObject.get("member_seq").getAsInt();
      String storeLoc = jsonObject.get("store_loc").getAsString();

      bossStoreLocDAO.updateStoreLoc(memberSeq, storeLoc);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("주소를 성공적으로 수정했습니다.", storeLoc), response, gson);
    } catch (JsonParseException e) {
      log.error("소개 변경 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (Exception e) {
      log.error("주소 변경 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("주소 변경 도중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }
}
