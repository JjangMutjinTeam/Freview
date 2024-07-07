package com.nuguna.freview.servlet.member.api.customer.myactivity;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nuguna.freview.dao.member.common.MemberUtilDAO;
import com.nuguna.freview.dao.member.cust.page.CustMyActivityDAO;
import com.nuguna.freview.dto.common.ResponseMessage;
import com.nuguna.freview.dto.cust.activitylog.CustMyLikePostDto;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.member.MemberGubun;
import com.nuguna.freview.exception.IllegalAgeGroupException;
import com.nuguna.freview.util.EncodingUtil;
import com.nuguna.freview.util.JsonResponseUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/customer/my-activity/liked-posts")
public class LikedPostsServlet extends HttpServlet {

  private Gson gson;
  private MemberUtilDAO memberUtilDAO;
  private CustMyActivityDAO custMyActivityDAO;

  @Override
  public void init() throws ServletException {
    gson = new Gson();
    memberUtilDAO = new MemberUtilDAO();
    custMyActivityDAO = new CustMyActivityDAO();
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    EncodingUtil.setEncodingToUTF8AndJson(request, response);
    try {
      // 세션 확인
      Member member = (Member) request.getSession().getAttribute("Member");
      int memberSeq = member.getMemberSeq();
      MemberGubun memberGubun = memberUtilDAO.selectMemberGubun(memberSeq);
      if (memberGubun == null || !memberGubun.isCust()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "유효하지 않은 유저입니다.");
        return;
      }
      List<CustMyLikePostDto> likePosts = custMyActivityDAO.getLikePosts(memberSeq);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_OK,
          new ResponseMessage<>("내가 좋아요한 포스트 정보를 가져왔습니다.", likePosts), response, gson);
    } catch (JsonParseException e) {
      log.error("내가 좋아요한 포스트 정보 요청에 대한 JSON 파싱 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("요청 JSON의 형식에 문제가 있습니다.", null), response, gson);
    } catch (IllegalAgeGroupException e) {
      log.error("유효하지 않은 내가 좋아요한 포스트 정보 요청입니다.");
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_BAD_REQUEST,
          new ResponseMessage<>("유효하지 않은 요청입니다.", null), response, gson);
    } catch (Exception e) {
      log.error("내가 좋아요한 포스트 정보를 가져오는 중 서버 에러가 발생했습니다.", e);
      JsonResponseUtil.sendBackJsonWithStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          new ResponseMessage<>("내가 좋아요한 포스트 정보를 가져오는 중 서버 에러가 발생했습니다.", null), response, gson);
    }
  }

  @Override
  public void destroy() {
  }
}
