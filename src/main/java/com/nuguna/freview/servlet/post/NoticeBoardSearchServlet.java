package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

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

  private NoticePostDAO noticePostDAO = new NoticePostDAO();
  private PostDAO postDAO = new PostDAO();
  private Gson gson = new Gson();

  private final int LIMIT = 10;

  @Override
  protected void doPost(HttpServletRequest reqeust, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(reqeust, response);

    String searchWord = reqeust.getParameter("searchWord");
    int pageNumber = getPageNumber(reqeust);
    String postGubun = PostGubun.GJ.getCode();
    List<Post> postList = noticePostDAO.getNoticeByPage(postGubun, pageNumber, LIMIT, searchWord);
    int totalPosts = postDAO.getTotalPostsCount(postGubun, searchWord);
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
