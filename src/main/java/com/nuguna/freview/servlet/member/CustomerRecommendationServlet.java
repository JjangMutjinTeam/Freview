package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.RecommendationMemberDAO;
import com.nuguna.freview.dto.MemberRecommendationInfo;
import com.nuguna.freview.entity.member.MemberGubun;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recommendation-customer")
public class CustomerRecommendationServlet extends HttpServlet {

  private RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();
  private final int LIMIT = 10;

  @Override
  protected void doGet(
      HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    int previousPostSeq = getPreviousPostSeq(req);
    String requestedMemberGubun = MemberGubun.CUSTOMER.getCode();
    List<MemberRecommendationInfo> customerInfoList = loadRecommendationLists(requestedMemberGubun, previousPostSeq);

    req.setAttribute("customerInfoList", customerInfoList);
    RequestDispatcher rd = req.getRequestDispatcher("/customer-recommendation-board-y.jsp");
    rd.forward(req, resp);
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
