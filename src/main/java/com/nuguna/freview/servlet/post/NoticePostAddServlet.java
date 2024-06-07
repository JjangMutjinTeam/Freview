package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/noticeBoard/createPost")
public class NoticePostAddServlet extends HttpServlet {
  PostDAO postDAO = new PostDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/admin-create-notice-y.jsp");
  }
}
