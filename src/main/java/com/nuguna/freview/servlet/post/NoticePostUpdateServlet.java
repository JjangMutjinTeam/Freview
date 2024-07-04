package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndUTF8;

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

@WebServlet("/notice-detail-update")
public class NoticePostUpdateServlet extends HttpServlet {

  private PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndUTF8(request, response);

    int postSeq = Integer.parseInt(request.getParameter("postSeq"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

    Post post = new Post();
    post.setPostSeq(postSeq);
    post.setTitle(title);
    post.setContent(content);
    post.setUpdatedAt(now);

    boolean updatePost = postDAO.updatePost(post);

    if (updatePost) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
