package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.dto.MojipPostDTO;
import com.nuguna.freview.entity.member.Member;
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

@WebServlet("/mojip")
public class MojipBoardServlet extends HttpServlet {

  private MojipPostDAO mojipPostDAO = new MojipPostDAO();
  private final int LIMIT = 12;
  private Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    //TODO: 비로그인 시 로그인페이지로 이동하는 메서드 유틸로 작성하기
    if (loginUser == null) {
      response.sendRedirect("common-login.jsp");
      return;
    }
    request.setAttribute("loginUser", loginUser);

    request.getRequestDispatcher("/common-mojip-board-y.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    //TODO: 페이지네이션 구현 때 활용
    int previousPostSeq = getPreviousPostSeq(request);
    List<MojipPostDTO> postList = loadMojipPosts(previousPostSeq);

    boolean hasMore = postList.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", postList);
    responseMap.put("hasMore", hasMore);

    sendJsonResponse(responseMap, response, gson);
  }

  private int getPreviousPostSeq(HttpServletRequest request) {
    int previousPostSeq = Integer.MAX_VALUE;
    if (request.getParameter("previousPostSeq") != null) {
      try {
        previousPostSeq = Integer.parseInt(request.getParameter("previousPostSeq"));
      } catch (NumberFormatException e) {
        previousPostSeq = Integer.MAX_VALUE;
      }
    }
    return previousPostSeq;
  }

  private List<MojipPostDTO> loadMojipPosts(int previousPostSeq) {
    return mojipPostDAO.getMojipPostList(previousPostSeq, LIMIT);
  }
}
