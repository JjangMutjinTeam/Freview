package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.post.Likes;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/likes-add")
public class PostLikesAddServlet extends HttpServlet {

  PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    Likes likes = new Likes();
    likes.setMemberSeq(Integer.valueOf(req.getParameter("memberSeq")));
    likes.setPostSeq(Integer.valueOf(req.getParameter("postSeq")));
    likes.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

    //TODO: 동시성 문제 고려
    boolean isInserted = postDAO.insertLikes(likes);

    if (isInserted) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
