package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.dto.MojipPostDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojipBoard")
public class MojipBoardServlet extends HttpServlet {

  MojipPostDAO mojipPostDAO = new MojipPostDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    List<MojipPostDTO> postList = mojipPostDAO.getMojipPostList();

    req.setAttribute("postList", postList);

    RequestDispatcher rd = req.getRequestDispatcher("/common-mojip-board-y.jsp");
    rd.forward(req, resp);
  }
}
