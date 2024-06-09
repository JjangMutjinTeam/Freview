package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.MemberDAO;
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

  MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    List<MemberRecommendationInfo> bossInfoList = memberDAO.selectMemberInfo(
        MemberGubun.BOSS.getCode());
    req.setAttribute("bossInfoList", bossInfoList);
    List<MemberRecommendationInfo> customerInfoList = memberDAO.selectMemberInfo(
        MemberGubun.CUST.getCode());
    req.setAttribute("customerInfoList", customerInfoList);
    RequestDispatcher rd = req.getRequestDispatcher("/common-recommendation-y.jsp");
    rd.forward(req, resp);
  }
}
