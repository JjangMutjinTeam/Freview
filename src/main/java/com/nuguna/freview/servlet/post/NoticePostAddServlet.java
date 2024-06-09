package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.entity.post.PostGubun.GJ;

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

@WebServlet("/noticeBoard/createPost")
public class NoticePostAddServlet extends HttpServlet {

  PostDAO postDAO = new PostDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/admin-create-notice-y.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

    Post post = new Post();
    post.setTitle(req.getParameter("title"));
    post.setContent(req.getParameter("content"));
    post.setGubun(GJ.getCode());
    post.setCreatedAt(now);
    post.setUpdatedAt(now);
    post.setMemberSeq(Integer.valueOf(req.getParameter("memberSeq")));

    boolean insertPost = postDAO.insertPost(post);

    if (insertPost) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
