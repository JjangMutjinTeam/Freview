package com.nuguna.freview.servlet.post;

import com.google.gson.Gson;
import com.nuguna.freview.dao.post.NoticePostDAO;
import com.nuguna.freview.dao.post.PostDAO;
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

@WebServlet("/notice-search")
public class NoticeBoardSearchServlet extends HttpServlet {

  NoticePostDAO noticePostDAO = new NoticePostDAO();
  PostDAO postDAO = new PostDAO();

  private final int LIMIT = 10;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json;charset=UTF-8");

    String searchStr = req.getParameter("search_str");
    int pageNumber = getPageNumber(req);
    String postGubun = PostGubun.GJ.getCode();
    List<Post> postList = noticePostDAO.getNoticeByPage(postGubun, pageNumber, LIMIT, searchStr);
    int totalPosts = postDAO.getTotalPostsCount(postGubun, searchStr);
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
