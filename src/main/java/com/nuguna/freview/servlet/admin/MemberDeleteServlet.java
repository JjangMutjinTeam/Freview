package com.nuguna.freview.servlet.admin;

import static com.nuguna.freview.util.ShaUtil.sha256Encoding;

import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.dao.member.MemberDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/admin-member-delete")
public class MemberDeleteServlet extends HttpServlet {

  private AdminDAO adminDAO = new AdminDAO();
  private MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int deleteMemberSeq = Integer.parseInt(req.getParameter("deleteMemberSeq"));
    String adminVerificationPW = sha256Encoding(req.getParameter("adminVerificationPW"));
    boolean isRightPassword = adminDAO.getMatchingMember(adminVerificationPW);

    if (isRightPassword) {
      boolean isDeleted = memberDAO.deleteMember(deleteMemberSeq);

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
