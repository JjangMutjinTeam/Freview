package com.nuguna.freview.servlet.member;

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

@WebServlet("/recommendation-filter")
public class RecommendationFilterServlet extends HttpServlet {

  RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();

  private final int LIMIT = 10;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String memberGubun = req.getParameter("memberGubun");
    String[] foodTypes = req.getParameterValues("foodType");
    String[] tags = req.getParameterValues("tag");

    int previousPostSeq = Integer.MAX_VALUE;
    if (req.getParameter("previousPostSeq") != null) {
      previousPostSeq = Integer.parseInt(req.getParameter("previousPostSeq"));
    }

    if (memberGubun.equals(MemberGubun.BOSS.getCode())) {
      List<MemberRecommendationInfo> bossInfoList = recommendationMemberDAO.filterMembers(
          memberGubun, previousPostSeq, LIMIT, foodTypes, tags);
      req.setAttribute("bossInfoList", bossInfoList);

      List<MemberRecommendationInfo> customerInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
          MemberGubun.CUSTOMER.getCode(), previousPostSeq, LIMIT);
      req.setAttribute("customerInfoList", customerInfoList);

    } else if (memberGubun.equals(MemberGubun.CUSTOMER.getCode())) {
      List<MemberRecommendationInfo> bossInfoList = recommendationMemberDAO.selectMemberByCursorPaging(
          MemberGubun.BOSS.getCode(), previousPostSeq, LIMIT);
      req.setAttribute("bossInfoList", bossInfoList);

      List<MemberRecommendationInfo> customerInfoList = recommendationMemberDAO.filterMembers(
          memberGubun, previousPostSeq, LIMIT, foodTypes, tags);
      req.setAttribute("customerInfoList", customerInfoList);


    } else {
      //TODO: 에러 발생 시
    }

    req.setAttribute("requestedMemberGubun", memberGubun);
    req.getRequestDispatcher("/common-recommendation-board-y.jsp").forward(req, resp);
  }
}