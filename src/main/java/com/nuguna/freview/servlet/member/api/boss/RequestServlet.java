package com.nuguna.freview.servlet.member.api.boss;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.BossRequestDAO;

import com.nuguna.freview.dto.api.boss.BossRequestPostListDto;
import com.nuguna.freview.dto.api.boss.BossRequestReceivedDto;
import com.nuguna.freview.dto.api.boss.BossRequestToRequestDto;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/boss/my-request")
public class RequestServlet extends HttpServlet {
    private Gson gson;
    private BossRequestDAO BossRequestDAO;

    public void init() {
      gson = new Gson();
      BossRequestDAO = new BossRequestDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

      HttpSession session = request.getSession();
      Member loginUser = (Member) session.getAttribute("Member");

      EncodingUtil.setEncodingToUTF8AndJson(request, response);

      try {
        EncodingUtil.setEncodingToUTF8AndJson(request, response);

        int memberSeq = loginUser.getMemberSeq();

        List<BossRequestPostListDto> postList = BossRequestDAO.bossPostList(memberSeq);
        List<BossRequestReceivedDto> ReceivedRequest = BossRequestDAO.bossReceivedRequest(memberSeq);
        List<BossRequestToRequestDto> ToRequestList = BossRequestDAO.bossToRequest(memberSeq);

//        List<BossRequestMozzipListDto> mozzipList = BossRequestDAO.bossMozzipList(bossSeq);
//        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
//            new ResponseMessage<>("내가 작성한 모집글 리스트 요청 완료.", mozzipList) , response, gson);
//
//        List<BossRequestReceivedDto> ReceivedRequest = BossRequestDAO.bossReceivedRequest(bossSeq);
//        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
//            new ResponseMessage<>("사장님에게 지원한 사람들의 리스트", ReceivedRequest) , response, gson);
//
//        List<BossRequestToRequestDto> ToRequestList = BossRequestDAO.bossToRequest(bossSeq);
//        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
//            new ResponseMessage<>("사장님이 제안한 사람들의 리스트", ToRequestList) , response, gson);

        // JSON으로 응답을 생성
        PrintWriter out = response.getWriter();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("bossMozzipList", postList);
        responseData.put("receivedRequest", ReceivedRequest);
        responseData.put("ToRequestList", ToRequestList);

        out.println(gson.toJson(responseData));
        out.flush();


      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (JsonParseException e) {
        log.error("리스트 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
            new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
      } catch (Exception e) {
        log.error("리스트 요청 도중 서버 에러가 발생했습니다.", e);
        JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            new ResponseMessage<>("찜 정보 요청 도중 서버 에러가 발생했습니다.", null), response, gson);
      }
    }

}
