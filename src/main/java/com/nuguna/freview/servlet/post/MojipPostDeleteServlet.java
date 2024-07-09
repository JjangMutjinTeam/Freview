package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndUTF8;

import com.nuguna.freview.dao.post.PostDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojip-detail-delete")
public class MojipPostDeleteServlet extends HttpServlet {

  private PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndUTF8(request, response);


    int postSeq = Integer.parseInt(request.getParameter("postSeq"));

    boolean deletePost = postDAO.deletePost(postSeq);

    if (deletePost) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
