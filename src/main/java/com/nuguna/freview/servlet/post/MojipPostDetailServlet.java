package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.dto.MojipPostDTO;
import com.nuguna.freview.entity.member.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mojip-detail")
public class MojipPostDetailServlet extends HttpServlet {

  private MojipPostDAO mojipPostDAO = new MojipPostDAO();
  private PostDAO postDAO = new PostDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int postSeq = Integer.parseInt(request.getParameter("postSeq"));

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      response.sendRedirect("common-login.jsp");
      return;
    }
    request.setAttribute("loginUser", loginUser);

    int memberSeq = loginUser.getMemberSeq();
    MojipPostDTO mojipPost = mojipPostDAO.getMojipPostOne(postSeq);
    boolean isLiked = postDAO.isLikedPost(memberSeq, postSeq);

    request.setAttribute("mojipPost", mojipPost);
    request.setAttribute("isLiked", isLiked);
    request.getRequestDispatcher("/common-mojip-post-detail-y.jsp").forward(request, response);
  }
}
