package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

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

  private NoticePostDAO noticePostDAO = new NoticePostDAO();
  private PostDAO postDAO = new PostDAO();
  private Gson gson = new Gson();

  private final int LIMIT = 10;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    request.setAttribute("loginUser", loginUser);

    request.getRequestDispatcher("/common-notice-board-y.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");

    int pageNumber = getPageNumber(request);
    String postGubun = PostGubun.GJ.getCode();
    List<Post> postList = noticePostDAO.getNoticeByPage(postGubun, pageNumber, LIMIT);
    int totalPosts = postDAO.getTotalPostsCount(postGubun);
    int totalPages = (int) Math.ceil((double) totalPosts / LIMIT);

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", postList);
    responseMap.put("totalPages", totalPages);

    sendJsonResponse(responseMap, response, gson);
  }

  private int getPageNumber(HttpServletRequest req) {
    try {
      return Integer.parseInt(req.getParameter("page"));
    } catch (NumberFormatException e) {
      return 1;
    }
  }
}
