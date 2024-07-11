package com.nuguna.freview.servlet.member;

import static com.nuguna.freview.util.ShaUtil.sha256Encoding;

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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int memberSeq = Integer.parseInt(request.getParameter("memberSeq"));
    String currentPassword = sha256Encoding(request.getParameter("currentPassword"));
    String newPassword = request.getParameter("newPassword");

    boolean isMatching = memberDAO.isMatchingMember(memberSeq, currentPassword);

    if (isMatching) {
      memberDAO.updatePassword(memberSeq, newPassword);
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
