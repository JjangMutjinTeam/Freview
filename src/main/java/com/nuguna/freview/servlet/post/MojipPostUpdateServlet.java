package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojipBoard/detail/update")
public class MojipPostUpdateServlet extends HttpServlet {

  PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    int postSeq = Integer.parseInt(req.getParameter("postSeq"));
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

    Post post = new Post();
    post.setPostSeq(postSeq);
    post.setTitle(title);
    post.setContent(content);
    post.setUpdatedAt(now);

    boolean updatePost = postDAO.updatePost(post);

    if (updatePost) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
