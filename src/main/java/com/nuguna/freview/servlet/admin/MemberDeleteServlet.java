package com.nuguna.freview.servlet.admin;

import com.nuguna.freview.dao.admin.AdminDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin-member-delete")
public class MemberDeleteServlet extends HttpServlet {

  private AdminDAO adminDAO = new AdminDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String deleteMemberId = req.getParameter("deleteMemberId");
    String adminVerificationPW = req.getParameter("adminVerificationPW");
    boolean isRightPassword = adminDAO.getMatchingMember(adminVerificationPW);

    if (isRightPassword) {
      boolean isDeleted = adminDAO.deleteMember(deleteMemberId);

      if (isDeleted) {
        resp.setStatus(HttpServletResponse.SC_OK);
      } else {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
    } else {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
