package com.nuguna.freview.servlet.admin;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;
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
import javax.servlet.http.HttpSession;

@WebServlet("/admin-member-management")
public class MemberManagingServlet extends HttpServlet {

  private AdminDAO adminDAO = new AdminDAO();
  private final int LIMIT = 30;
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

    request.getRequestDispatcher("/admin-management-member-y.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    int previousMemberSeq = getPreviousMemberSeq(request);
    List<Member> memberList = loadMemberList(previousMemberSeq);
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

  private List<Member> loadMemberList(int previousMemberSeq) {
    return adminDAO.getMemberList(previousMemberSeq, LIMIT);
  }
}
