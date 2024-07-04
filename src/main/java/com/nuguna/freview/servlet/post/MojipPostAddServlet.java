package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.entity.post.PostGubun.MJ;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndUTF8;

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

@WebServlet("/mojip-create")
public class MojipPostAddServlet extends HttpServlet {

  private MojipPostDAO mojipPostDAO = new MojipPostDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/boss-create-mojip-y.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndUTF8(request, response);

    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

    Post post = new Post();
    post.setTitle(request.getParameter("title"));
    post.setMemberSeq(Integer.valueOf(request.getParameter("memberSeq")));
    post.setApplyStartDate(Date.valueOf(request.getParameter("applyStartDate")));
    post.setApplyEndDate(Date.valueOf(request.getParameter("applyEndDate")));
    post.setExperienceDate(Date.valueOf(request.getParameter("experienceDate")));
    post.setContent(request.getParameter("content"));
    post.setGubun(MJ.getCode());
    post.setThumbnailPhotoUrl(request.getParameter("thumbnailPhotoUrl"));
    post.setCreatedAt(now);
    post.setUpdatedAt(now);

    boolean insertPost = mojipPostDAO.insertMojipPost(post);

    if (insertPost) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
