package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.dto.MojipPostDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojipboard/detail")
public class MojipPostDetailServlet extends HttpServlet {

  MojipPostDAO mojipPostDAO = new MojipPostDAO();
  PostDAO postDAO = new PostDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int postSeq = Integer.parseInt(req.getParameter("postSeq"));
    int memberSeq = 11; //TODO: 로그인한 사용자의 memberSeq 필요

    MojipPostDTO mojipPost = mojipPostDAO.getMojipPost(postSeq);
    boolean isLiked = postDAO.isLikedPost(memberSeq, postSeq);

    req.setAttribute("mojipPost", mojipPost);
    req.setAttribute("isLiked", isLiked);
    req.getRequestDispatcher("/common-mojip-post-detail-y.jsp").forward(req, resp);
  }
}
