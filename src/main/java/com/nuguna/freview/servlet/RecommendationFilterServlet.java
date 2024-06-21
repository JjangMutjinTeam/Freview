package com.nuguna.freview.servlet;

import com.nuguna.freview.dao.member.MemberDAO;
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
  MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("doGet 들어왔다!");
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String memberGubun = req.getParameter("memberGubun");
    String[] foodTypes = req.getParameterValues("foodType");
    String[] tags = req.getParameterValues("tag");

    if (memberGubun.equals(MemberGubun.BOSS.getCode())) {
      List<MemberRecommendationInfo> bossInfoList = recommendationMemberDAO.filterMembers(
          memberGubun, foodTypes, tags);
      req.setAttribute("bossInfoList", bossInfoList);
      List<MemberRecommendationInfo> customerInfoList = memberDAO.selectMemberInfo(
          MemberGubun.CUSTOMER.getCode());
      req.setAttribute("customerInfoList", customerInfoList);
    } else if (memberGubun.equals(MemberGubun.CUSTOMER.getCode())) {
      List<MemberRecommendationInfo> bossInfoList = memberDAO.selectMemberInfo(
          MemberGubun.BOSS.getCode());
      req.setAttribute("bossInfoList", bossInfoList);
      List<MemberRecommendationInfo> customerInfoList = recommendationMemberDAO.filterMembers(
          memberGubun, foodTypes, tags);
      req.setAttribute("customerInfoList", customerInfoList);
    } else {
      //TODO: 에러 발생 시
    }

    req.setAttribute("requestedMemberGubun", memberGubun);
    req.getRequestDispatcher("/common-recommendation-y.jsp").forward(req, resp);
  }
}