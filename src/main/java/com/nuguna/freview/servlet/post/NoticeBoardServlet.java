package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/noticeBoard")
public class NoticeBoardServlet extends HttpServlet {

  PostDAO postDAO = new PostDAO();

  private final int LIMIT = 10;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String postGubun = "GJ";
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    int previousPostSeq = Integer.MAX_VALUE;
    if (req.getParameter("previousPostSeq") != null) {
      previousPostSeq = Integer.parseInt(req.getParameter("previousPostSeq"));
    }

    List<Post> postList = postDAO.selectNoticePostByCursorPaging(postGubun, previousPostSeq, LIMIT);
    req.setAttribute("postList", postList);

    RequestDispatcher rd = req.getRequestDispatcher("/common-notice-board-y.jsp");
    rd.forward(req, resp);
  }
}
