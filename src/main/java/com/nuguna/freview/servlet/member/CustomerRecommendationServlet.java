package com.nuguna.freview.servlet.member;

import com.google.gson.Gson;
import com.nuguna.freview.dao.member.RecommendationMemberDAO;
import com.nuguna.freview.dto.MemberRecommendationInfo;
import com.nuguna.freview.entity.member.MemberGubun;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recommendation-customer")
public class CustomerRecommendationServlet extends HttpServlet {

  private RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();
  private final int LIMIT = 20;

  @Override
  protected void doGet(
      HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    int previousPostSeq = getPreviousMemberSeq(req);
    String requestedMemberGubun = MemberGubun.CUSTOMER.getCode();
    List<MemberRecommendationInfo> customerInfoList = loadRecommendationLists(requestedMemberGubun, previousPostSeq);

    req.setAttribute("customerInfoList", customerInfoList);
    req.getRequestDispatcher("/customer-recommendation-board-y.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json;charset=utf-8");

    int previousMemberSeq = getPreviousMemberSeq(req);
    String requestedMemberGubun = MemberGubun.CUSTOMER.getCode();
    List<MemberRecommendationInfo> customerInfoList = loadRecommendationLists(requestedMemberGubun, previousMemberSeq);

    Gson gson = new Gson();
    String str = gson.toJson(customerInfoList);

    resp.getWriter().write(str);
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
