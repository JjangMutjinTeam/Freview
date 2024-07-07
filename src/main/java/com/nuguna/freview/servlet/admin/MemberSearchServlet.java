package com.nuguna.freview.servlet.admin;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.admin.AdminDAO;
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

@WebServlet("/admin-member-search")
public class MemberSearchServlet extends HttpServlet {

  private AdminDAO adminDAO = new AdminDAO();
  private Gson gson = new Gson();

  private final int LIMIT = 30;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    int previousMemberSeq = getPreviousMemberSeq(request);
    String searchWord = request.getParameter("searchWord");
    List<Member> memberList = loadMemberList(previousMemberSeq, searchWord);
    boolean hasMore = memberList.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", memberList);
    responseMap.put("hasMore", hasMore);

    sendJsonResponse(responseMap, response, gson);
  }

  private int getPreviousMemberSeq(HttpServletRequest request) {
    int previousMemberSeq = Integer.MAX_VALUE;
    if (request.getParameter("previousMemberSeq") != null) {
      try {
        previousMemberSeq = Integer.parseInt(request.getParameter("previousMemberSeq"));
      } catch (NumberFormatException e) {
        previousMemberSeq = Integer.MAX_VALUE;
      }
    }
    return previousMemberSeq;
  }

  private List<Member> loadMemberList(int previousMemberSeq, String searchWord) {
    return adminDAO.getMemberList(previousMemberSeq, LIMIT, searchWord);
  }
}
