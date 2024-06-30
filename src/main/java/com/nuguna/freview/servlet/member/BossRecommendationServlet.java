package com.nuguna.freview.servlet.member;

import com.google.gson.Gson;
import com.nuguna.freview.dao.member.RecommendationMemberDAO;
import com.nuguna.freview.dto.MemberRecommendationInfo;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.member.MemberGubun;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/recommendation-boss")
public class BossRecommendationServlet extends HttpServlet {

  private final RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();
  private final int LIMIT = 20;

  @Override
  protected void doGet(
      HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    int previousPostSeq = getPreviousPostSeq(req);
    String requestedMemberGubun = MemberGubun.BOSS.getCode();
    List<MemberRecommendationInfo> bossInfoList = loadRecommendationLists(requestedMemberGubun, previousPostSeq);
    req.setAttribute("bossInfoList", bossInfoList);

    HttpSession session = req.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      resp.sendRedirect("common-login.jsp");
      return;
    }

    req.setAttribute("loginUser", loginUser);

    req.getRequestDispatcher("/boss-recommendation-board-y.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json;charset=utf-8");

    int previousPostSeq = getPreviousPostSeq(req);
    String requestedMemberGubun = MemberGubun.BOSS.getCode();
    List<MemberRecommendationInfo> bossInfoList = loadRecommendationLists(requestedMemberGubun, previousPostSeq);

    boolean hasMore = bossInfoList.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", bossInfoList);
    responseMap.put("hasMore", hasMore);

    Gson gson = new Gson();
    String str = gson.toJson(responseMap);

    resp.getWriter().write(str);
  }

  private int getPreviousPostSeq(HttpServletRequest req) {
    int previousPostSeq = Integer.MAX_VALUE;
    if (req.getParameter("previousPostSeq") != null) {
      try {
        previousPostSeq = Integer.parseInt(req.getParameter("previousPostSeq"));
      } catch (NumberFormatException e) {
        previousPostSeq = Integer.MAX_VALUE;
      }
    }
    return previousPostSeq;
  }

  private List<MemberRecommendationInfo> loadRecommendationLists(String memberGubun,
      int previousPostSeq) {
    List<MemberRecommendationInfo> list = recommendationMemberDAO.selectMemberByCursorPaging(
        memberGubun, previousPostSeq, LIMIT);
    return list;
  }
}
