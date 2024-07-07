package com.nuguna.freview.servlet.admin;

import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.member.MemberGubun;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      response.sendRedirect("common-login.jsp");
      return;
    } else if (loginUser.getGubun() != MemberGubun.ADMIN.getCode()) {
      response.sendRedirect("common-error-404.jsp");
      return;
    }
    request.setAttribute("loginUser", loginUser);

    request.getRequestDispatcher("/admin-member-management").forward(request, response);
  }
}
