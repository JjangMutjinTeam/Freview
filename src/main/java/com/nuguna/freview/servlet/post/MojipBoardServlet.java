package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;

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

@WebServlet("/mojip")
public class MojipBoardServlet extends HttpServlet {

  private MojipPostDAO mojipPostDAO = new MojipPostDAO();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    List<MojipPostDTO> postList = mojipPostDAO.getMojipPostList();

    request.setAttribute("postList", postList);

    RequestDispatcher rd = request.getRequestDispatcher("/common-mojip-board-y.jsp");
    rd.forward(request, response);
  }
}
