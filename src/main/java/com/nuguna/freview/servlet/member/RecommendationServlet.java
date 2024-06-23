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

@WebServlet("/recommendation")
public class RecommendationServlet extends HttpServlet {

  RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();

  private final int LIMIT = 10;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String requestedMemberGubun = "B"; // 기본값 설정
    if (req.getParameter("requestedMemberGubun") != null) {
      requestedMemberGubun = req.getParameter("requestedMemberGubun");
    }
    req.setAttribute("requestedMemberGubun", requestedMemberGubun);

    int previousPostSeq = Integer.MAX_VALUE;
    if (req.getParameter("previousPostSeq") != null) {
      previousPostSeq = Integer.parseInt(req.getParameter("previousPostSeq"));
    }

    List<MemberRecommendationInfo> bossInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
        MemberGubun.BOSS.getCode(), previousPostSeq, LIMIT);
    req.setAttribute("bossInfoList", bossInfoList);
    List<MemberRecommendationInfo> customerInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
        MemberGubun.CUSTOMER.getCode(), previousPostSeq, LIMIT);
    req.setAttribute("customerInfoList", customerInfoList);
    RequestDispatcher rd = req.getRequestDispatcher("/common-recommendation-board-y.jsp");
    rd.forward(req, resp);
  }
}
