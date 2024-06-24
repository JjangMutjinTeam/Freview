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

//  private RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();
//  private final int LIMIT = 10;
//
//  @Override
//  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    req.setCharacterEncoding("UTF-8");
//    resp.setContentType("text/html;charset=UTF-8");
//
//    String requestedMemberGubun = getRequestedMemberGubun(req);
//    req.setAttribute("requestedMemberGubun", requestedMemberGubun);
//
//    int previousPostSeq = getPreviousPostSeq(req);
//    loadRecommendationLists(req, requestedMemberGubun, previousPostSeq);
//
//    RequestDispatcher rd = req.getRequestDispatcher("/boss-recommendation-board-y.jsp");
//    rd.forward(req, resp);
//  }
//
//  private String getRequestedMemberGubun(HttpServletRequest req) {
//    String requestedMemberGubun = "B"; // 기본값 설정
//    if (req.getParameter("requestedMemberGubun") != null) {
//      requestedMemberGubun = req.getParameter("requestedMemberGubun");
//    }
//    return requestedMemberGubun;
//  }
//
//  private int getPreviousPostSeq(HttpServletRequest req) {
//    int previousPostSeq = Integer.MAX_VALUE;
//    if (req.getParameter("previousPostSeq") != null) {
//      try {
//        previousPostSeq = Integer.parseInt(req.getParameter("previousPostSeq"));
//      } catch (NumberFormatException e) {
//        previousPostSeq = Integer.MAX_VALUE;
//      }
//    }
//    return previousPostSeq;
//  }
//
//  private void loadRecommendationLists(HttpServletRequest req, String memberGubun, int previousPostSeq) {
//    if ("B".equals(memberGubun)) {
//      List<MemberRecommendationInfo> bossInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
//          MemberGubun.BOSS.getCode(), previousPostSeq, LIMIT);
//      req.setAttribute("bossInfoList", bossInfoList);
//
//      List<MemberRecommendationInfo> customerInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
//          MemberGubun.CUSTOMER.getCode(), previousPostSeq, LIMIT);
//      req.setAttribute("customerInfoList", customerInfoList);
//    } else if ("C".equals(memberGubun)) {
//      List<MemberRecommendationInfo> customerInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
//          MemberGubun.CUSTOMER.getCode(), previousPostSeq, LIMIT);
//      req.setAttribute("customerInfoList", customerInfoList);
//
//      List<MemberRecommendationInfo> bossInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
//          MemberGubun.BOSS.getCode(), previousPostSeq, LIMIT);
//      req.setAttribute("bossInfoList", bossInfoList);
//    }
//  }
}

