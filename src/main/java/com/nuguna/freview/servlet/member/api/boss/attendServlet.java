package com.nuguna.freview.servlet.member.api.boss;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossAttendDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mariadb.jdbc.util.log.Logger;

@WebServlet("/api/boss/attend")
public class attendServlet extends HttpServlet {

  private Gson gson;
  private BossAttendDAO BossAttendDAO;
  private Logger log;

  public void init() {
    gson = new Gson();
    BossAttendDAO = new BossAttendDAO();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("doPost진입");

    //TODO: jsp에서 postSeq 갖고오기
    int postSeq = Integer.parseInt(req.getParameter("postSeq"));
    System.out.println(postSeq);

    try {
      EncodingUtil.setEncodingToUTF8AndJson(req, resp);

      boolean insertAttend = BossAttendDAO.bossAttend(postSeq);

      if (insertAttend) {
        resp.setStatus(HttpServletResponse.SC_OK);
      } else {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (JsonParseException e) {
      log.error("참석 여부 등록 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), resp, gson);
    } catch (Exception e) {
      log.error(" 참석 여부 등록 요청 도중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("찜 정보 요청 도중 서버 에러가 발생했습니다.", null), resp, gson);
    }

  }
}
