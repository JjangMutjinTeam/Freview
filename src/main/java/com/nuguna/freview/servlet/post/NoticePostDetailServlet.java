package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice/detail")
public class NoticePostDetailServlet extends HttpServlet {

  PostDAO postDAO = new PostDAO();
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    HttpSession session = req.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      resp.sendRedirect("/common-login.jsp");
      return;
    }

    req.setAttribute("loginUser", loginUser);
    int postSeq = Integer.parseInt(req.getParameter("postId"));
    Post currentPost = postDAO.selectPostByPostSeq(postSeq);
    req.setAttribute("currentPost", currentPost);
    req.getRequestDispatcher("/common-notice-post-detail-y.jsp").forward(req, resp);
  }
}
