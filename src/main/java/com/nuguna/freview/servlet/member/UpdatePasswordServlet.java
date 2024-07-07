package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.MemberDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/password-update")
public class UpdatePasswordServlet extends HttpServlet {

  private MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    int memberSeq = Integer.parseInt(req.getParameter("memberSeq"));
    String currentPassword = req.getParameter("currentPassword");
    String newPassword = req.getParameter("newPassword");

    boolean isMatching = memberDAO.isMatchingMember(memberSeq, currentPassword);

    if (isMatching) {
      memberDAO.updatePassword(memberSeq, newPassword);
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
