package com.nuguna.freview.servlet.member;

import com.google.gson.Gson;
import com.nuguna.freview.dao.member.RecommendationMemberDAO;
import com.nuguna.freview.dto.MemberRecommendationInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recommendation-filter")
public class RecommendationFilterServlet extends HttpServlet {

  private final RecommendationMemberDAO recommendationMemberDAO = new RecommendationMemberDAO();
  private final int LIMIT = 10;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String memberGubun = req.getParameter("memberGubun");
    String[] foodTypes = req.getParameterValues("foodType");
    String[] tags = req.getParameterValues("tag");

    int previousMemberSeq = getPreviousMemberSeq(req);
    List<MemberRecommendationInfo> list = loadFilteredRecommendationLists(req, memberGubun, previousMemberSeq, foodTypes, tags);

    boolean hasMore = list.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", list);
    responseMap.put("hasMore", hasMore);

    Gson gson = new Gson();
    String str = gson.toJson(responseMap);

    resp.getWriter().write(str);
  }

  private int getPreviousMemberSeq(HttpServletRequest req) {
    int previousPostSeq = Integer.MAX_VALUE;
    if (req.getParameter("previousPostSeq") != null) {
      try {
        previousPostSeq = Integer.parseInt(req.getParameter("previousPostSeq"));
      } catch (NumberFormatException e) {
        previousPostSeq = Integer.MAX_VALUE;
      }
    }
    return previousPostSeq;
  }

  private List<MemberRecommendationInfo> loadFilteredRecommendationLists(HttpServletRequest req, String memberGubun,
      int previousPostSeq, String[] foodTypes, String[] tags) {
      return recommendationMemberDAO.filterMembers(
          memberGubun, previousPostSeq, LIMIT, foodTypes, tags);
  }
}
