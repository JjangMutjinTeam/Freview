package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notice-detail-delete")
public class NoticePostDeleteServlet extends HttpServlet {

  private PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    int postSeq = Integer.parseInt(request.getParameter("postSeq"));

    boolean deletePost = postDAO.deletePost(postSeq);

    if (deletePost) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
