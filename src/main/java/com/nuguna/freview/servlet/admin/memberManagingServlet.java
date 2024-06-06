package com.nuguna.freview.servlet.admin;

import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.entity.member.Member;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminPage/user")
public class memberManagingServlet extends HttpServlet {

  AdminDAO adminDAO = new AdminDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");
    List<Member> memberAllList = adminDAO.selectAllMember();
    req.setAttribute("memberAllList", memberAllList);
    RequestDispatcher rd = req.getRequestDispatcher("/admin-mg-users-y.jsp");
    rd.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String userId = req.getParameter("userId");
    String inputPassword = req.getParameter("password");
    String adminPassword = adminDAO.selectAdminPW();

    if (isPasswordMatch(inputPassword, adminPassword)) {
      boolean isDeleted = adminDAO.deleteMember(userId);

      if (isDeleted) {
        resp.setStatus(HttpServletResponse.SC_OK);
      } else {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
    } else {
      resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }

  private boolean isPasswordMatch(String inputPassword, String adminPassword) {
    return inputPassword.equals(adminPassword);
  }
}
