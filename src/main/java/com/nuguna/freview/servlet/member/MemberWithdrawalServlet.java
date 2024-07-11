package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.MemberDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/api/member-withdrawal")
public class MemberWithdrawalServlet extends HttpServlet {

  private MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int deleteMemberSeq = Integer.parseInt(req.getParameter("memberSeq"));

    boolean isDeleted = memberDAO.deleteMember(deleteMemberSeq);

    if (isDeleted) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
