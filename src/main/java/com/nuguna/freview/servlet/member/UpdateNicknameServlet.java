package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.MemberDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nickname-update")
public class UpdateNicknameServlet extends HttpServlet {

  private MemberDAO memberDAO = new MemberDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int memberSeq = Integer.parseInt(request.getParameter("memberSeq"));
    String newNickname = request.getParameter("newNickname");

    boolean isDuplicate = memberDAO.isDuplicateNickName(newNickname);

    if (!isDuplicate) {
      memberDAO.updateNickname(memberSeq, newNickname);
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
