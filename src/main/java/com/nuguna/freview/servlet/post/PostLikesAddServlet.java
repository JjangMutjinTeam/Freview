package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndUTF8;

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

  private PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndUTF8(request, response);

    Likes likes = new Likes();
    likes.setMemberSeq(Integer.valueOf(request.getParameter("memberSeq")));
    likes.setPostSeq(Integer.valueOf(request.getParameter("postSeq")));
    likes.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

    //TODO: 동시성 문제 고려
    boolean isInserted = postDAO.insertLikes(likes);

    if (isInserted) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
