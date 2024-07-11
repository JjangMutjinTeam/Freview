package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice-detail")
public class NoticePostDetailServlet extends HttpServlet {

  private PostDAO postDAO = new PostDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    request.setAttribute("loginUser", loginUser);
    int postSeq = Integer.parseInt(request.getParameter("postId"));
    Post currentPost = postDAO.selectPostByPostSeq(postSeq);
    request.setAttribute("currentPost", currentPost);

    boolean isLiked = postDAO.isLikedPost(loginUser.getMemberSeq(), postSeq);
    request.setAttribute("isLiked", isLiked);
    request.getRequestDispatcher("/common-notice-post-detail-y.jsp").forward(request, response);
  }
}
