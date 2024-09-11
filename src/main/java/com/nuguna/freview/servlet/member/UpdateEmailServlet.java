package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.MemberDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update-email")
public class UpdateEmailServlet extends HttpServlet {

  private MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int memberSeq = Integer.parseInt(request.getParameter("memberSeq"));
    String newEmail = request.getParameter("email");

    try {
      memberDAO.updateEmail(memberSeq, newEmail);
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
