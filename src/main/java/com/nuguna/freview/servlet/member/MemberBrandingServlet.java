package com.nuguna.freview.servlet.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MemberBrandingServlet")
public class MemberBrandingServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String gubun = req.getParameter("gubun");
    String mid = req.getParameter("mid");

    switch (gubun) {
      case "B":
        resp.sendRedirect(req.getContextPath() + "/boss-branding-page.jsp?mid=" + mid);
        break;
      case "C":
        resp.sendRedirect(req.getContextPath() + "/customer-branding-page.jsp?mid=" + mid);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user type");
        break;
    }
  }
}
