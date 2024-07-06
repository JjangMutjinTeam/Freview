package com.nuguna.freview.servlet.member;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;

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

@WebServlet("/recommendation-customer")
public class CustomerRecommendationServlet extends HttpServlet {

  private RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();
  private final int LIMIT = 20;

  @Override
  protected void doGet(
      HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    int previousPostSeq = getPreviousMemberSeq(request);
    String requestedMemberGubun = MemberGubun.CUSTOMER.getCode();
    List<MemberRecommendationInfo> customerInfoList = loadRecommendationLists(requestedMemberGubun, previousPostSeq);
    request.setAttribute("customerInfoList", customerInfoList);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      response.sendRedirect("common-login.jsp");
      return;
    }

    request.setAttribute("loginUser", loginUser);

    request.getRequestDispatcher("/customer-recommendation-board-y.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    int previousMemberSeq = getPreviousMemberSeq(request);
    String requestedMemberGubun = MemberGubun.CUSTOMER.getCode();
    List<MemberRecommendationInfo> customerInfoList = loadRecommendationLists(requestedMemberGubun, previousMemberSeq);

    boolean hasMore = customerInfoList.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", customerInfoList);
    responseMap.put("hasMore", hasMore);

    Gson gson = new Gson();
    String str = gson.toJson(responseMap);

    response.getWriter().write(str);
  }

  private int getPreviousMemberSeq(HttpServletRequest req) {
    int previousMemberSeq = Integer.MAX_VALUE;
    if (req.getParameter("previousMemberSeq") != null) {
      try {
        previousMemberSeq = Integer.parseInt(req.getParameter("previousMemberSeq"));
      } catch (NumberFormatException e) {
        previousMemberSeq = Integer.MAX_VALUE;
      }
    }
    return previousMemberSeq;
  }

  private List<MemberRecommendationInfo> loadRecommendationLists(String memberGubun,
      int previousPostSeq) {
    List<MemberRecommendationInfo> list = recommendationMemberDAO.selectMemberByCursorPaging(
        memberGubun, previousPostSeq, LIMIT);
    return list;
  }
}
