package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.entity.post.PostGubun.MJ;

import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojipBoard/createPost")
public class MojipPostAddServlet extends HttpServlet {

  MojipPostDAO mojipPostDAO = new MojipPostDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/boss-create-mojip-y.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

    Post post = new Post();
    post.setTitle(req.getParameter("title"));
    post.setMemberSeq(Integer.valueOf(req.getParameter("memberSeq")));
    post.setApplyStartDate(Date.valueOf(req.getParameter("applyStartDate")));
    post.setApplyEndDate(Date.valueOf(req.getParameter("applyEndDate")));
    post.setExperienceDate(Date.valueOf(req.getParameter("experienceDate")));
    post.setContent(req.getParameter("content"));
    post.setGubun(MJ.getCode());
    post.setThumbnailPhotoUrl(req.getParameter("thumbnailPhotoUrl"));
    post.setCreatedAt(now);
    post.setUpdatedAt(now);

    boolean insertPost = mojipPostDAO.insertMojipPost(post);

    if (insertPost) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
