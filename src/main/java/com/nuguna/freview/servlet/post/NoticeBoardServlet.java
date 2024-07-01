package com.nuguna.freview.servlet.post;

import com.google.gson.Gson;
import com.nuguna.freview.dao.post.NoticePostDAO;
import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import com.nuguna.freview.entity.post.PostGubun;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice")
public class NoticeBoardServlet extends HttpServlet {

  NoticePostDAO noticePostDAO = new NoticePostDAO();
  PostDAO postDAO = new PostDAO();

  private final int LIMIT = 10;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    HttpSession session = req.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      resp.sendRedirect("common-login.jsp");
      return;
    }

    req.setAttribute("loginUser", loginUser);

    req.getRequestDispatcher("/common-notice-board-y.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json;charset=UTF-8");

    int pageNumber = getPageNumber(req);
    String postGubun = PostGubun.GJ.getCode();
    List<Post> postList = noticePostDAO.getNoticeByPage(postGubun, pageNumber, LIMIT);
    int totalPosts = postDAO.getTotalPostsCount(postGubun);
    int totalPages = (int) Math.ceil((double) totalPosts / LIMIT);

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", postList);
    responseMap.put("totalPages", totalPages);

    Gson gson = new Gson();
    String jsonResponse = gson.toJson(responseMap);
    resp.getWriter().write(jsonResponse);
  }

  private int getPageNumber(HttpServletRequest req) {
    try {
      return Integer.parseInt(req.getParameter("page"));
    } catch (NumberFormatException e) {
      return 1;
    }
  }
}
