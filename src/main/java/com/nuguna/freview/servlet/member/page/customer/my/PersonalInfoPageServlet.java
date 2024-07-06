package com.nuguna.freview.servlet.member.page.customer.my;

import com.nuguna.freview.dao.member.common.MemberUtilDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.member.MemberGubun;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/my-personal-info")
public class PersonalInfoPageServlet extends HttpServlet {

  private MemberUtilDAO memberUtilDAO;

  @Override
  public void init() throws ServletException {
    memberUtilDAO = new MemberUtilDAO();
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      // 세션 확인
      Member member = (Member) request.getSession().getAttribute("Member");
      int memberSeq = member.getMemberSeq();
      MemberGubun memberGubun = memberUtilDAO.selectMemberGubun(memberSeq);
      if (memberGubun == null || !memberGubun.isCust()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "유효하지 않은 유저입니다.");
        return;
      }

      log.info("mylikesInfo = ");
      request.setAttribute("member_seq", memberSeq);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/customer-my-personal-info.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          e.getMessage());
    }
  }
}