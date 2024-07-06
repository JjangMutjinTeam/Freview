package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.entity.post.PostGubun.MJ;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndUTF8;

import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/mojip-create")
public class MojipPostAddServlet extends HttpServlet {

  private MojipPostDAO mojipPostDAO = new MojipPostDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      response.sendRedirect("common-login.jsp");
      return;
    }
    request.setAttribute("loginUser", loginUser);

    request.getRequestDispatcher("/boss-create-mojip-y.jsp").forward(request, response);
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
