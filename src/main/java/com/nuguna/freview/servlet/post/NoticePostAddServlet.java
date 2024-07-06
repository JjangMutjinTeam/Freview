package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.entity.post.PostGubun.GJ;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;

import com.nuguna.freview.dao.post.NoticePostDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice-create")
public class NoticePostAddServlet extends HttpServlet {

  private NoticePostDAO noticePostdao = new NoticePostDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      response.sendRedirect("/common-login.jsp");
      return;
    }

    request.setAttribute("loginUser", loginUser);
    response.sendRedirect(request.getContextPath() + "/admin-create-notice-y.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    HttpSession session = req.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      resp.sendRedirect("/common-login.jsp");
      return;
    }

    req.setAttribute("loginUser", loginUser);
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());

    Post post = new Post();
    post.setTitle(req.getParameter("title"));
    post.setContent(req.getParameter("content"));
    post.setGubun(GJ.getCode());
    post.setCreatedAt(now);
    post.setUpdatedAt(now);
    post.setMemberSeq(loginUser.getMemberSeq());

    boolean insertPost = noticePostdao.insertNoticePost(post);

    if (insertPost) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
