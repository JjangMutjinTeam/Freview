package com.nuguna.freview.servlet.member;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.member.RecommendationMemberDAO;
import com.nuguna.freview.dto.MemberRecommendationInfoDTO;
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
  private Gson gson = new Gson();

  @Override
  protected void doGet(
      HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    int previousPostSeq = getPreviousMemberSeq(request);
    String requestedMemberGubun = MemberGubun.BOSS.getCode();
    List<MemberRecommendationInfoDTO> bossInfoList = loadRecommendationLists(requestedMemberGubun,
        previousPostSeq);
    request.setAttribute("bossInfoList", bossInfoList);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    request.setAttribute("loginUser", loginUser);
    request.getRequestDispatcher("/boss-recommendation-board-y.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    int previousMemberSeq = getPreviousMemberSeq(request);
    String requestedMemberGubun = MemberGubun.BOSS.getCode();
    List<MemberRecommendationInfoDTO> bossInfoList = loadRecommendationLists(requestedMemberGubun,
        previousMemberSeq);

    boolean hasMore = bossInfoList.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", bossInfoList);
    responseMap.put("hasMore", hasMore);

    sendJsonResponse(responseMap, response, gson);
  }

  private int getPreviousMemberSeq(HttpServletRequest request) {
    int previousPostSeq = Integer.MAX_VALUE;
    if (request.getParameter("previousMemberSeq") != null) {
      try {
        previousPostSeq = Integer.parseInt(request.getParameter("previousMemberSeq"));
      } catch (NumberFormatException e) {
        previousPostSeq = Integer.MAX_VALUE;
      }
    }
    return previousPostSeq;
  }

  private List<MemberRecommendationInfoDTO> loadRecommendationLists(String memberGubun,
      int previousPostSeq) {
    return recommendationMemberDAO.selectMemberByCursorPaging(
        memberGubun, previousPostSeq, LIMIT);
  }
}
