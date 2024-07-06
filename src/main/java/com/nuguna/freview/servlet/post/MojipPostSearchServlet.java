package com.nuguna.freview.servlet.post;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.post.MojipPostDAO;
import com.nuguna.freview.dto.MojipPostDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojip-search")
public class MojipPostSearchServlet extends HttpServlet {

  private MojipPostDAO mojipPostDAO = new MojipPostDAO();
  private Gson gson = new Gson();

  //TODO: 전역변수 설정
  private final int LIMIT = 12;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    int previousPostSeq = getPreviousPostSeq(request);
    String searchWord = request.getParameter("searchWord");
    List<MojipPostDTO> postList = loadMojipPosts(previousPostSeq, searchWord);
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

  private List<MojipPostDTO> loadMojipPosts(int previousPostSeq, String searchWord) {
    return mojipPostDAO.getMojipPostList(previousPostSeq, LIMIT, searchWord);
  }
}
