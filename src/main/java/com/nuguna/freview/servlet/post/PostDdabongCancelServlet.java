package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.PostDAO;
import com.nuguna.freview.entity.post.Ddabong;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ddabongCancel")
public class PostDdabongCancelServlet extends HttpServlet {

  PostDAO postDAO = new PostDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("doPost 진입");
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    Ddabong ddabong = new Ddabong();

    //TODO: applicant -> '방문자'로 이름 수정할 것
    ddabong.setMemberSeq(Integer.valueOf(req.getParameter("applicantSeq")));
    ddabong.setPostSeq(Integer.valueOf(req.getParameter("postSeq")));

    boolean isDeleted = postDAO.deleteDdabong(ddabong);

    if (isDeleted) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
