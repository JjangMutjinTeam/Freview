package com.nuguna.freview.servlet.member;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.dto.AdminPersonalInfoDTO;
import com.nuguna.freview.entity.member.Member;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/personal-info-update")
public class UpdatePersonalInfoServlet extends HttpServlet {

  private AdminDAO adminDAO = new AdminDAO();
  private Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    if (loginUser == null) {
      response.sendRedirect("common-login.jsp");
      return;
    }

    request.setAttribute("loginUser", loginUser);

    //TODO: 관리자 외 멤버(사장님, 체험단)에 대한 추가 로직 구현
    switch(loginUser.getGubun()) {
      case "A" : request.getRequestDispatcher("/admin-update-personal-info-y.jsp").forward(request, response);
      break;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    //TODO: 관리자 외 멤버(사장님, 체험단)에 대한 추가 로직 구현

    try {
      setEncodingToUTF8AndJson(request, response);

      int memberSeq = Integer.parseInt(request.getParameter("memberSeq"));
      AdminPersonalInfoDTO memberInfo = adminDAO.getAdminPersonalInfo(memberSeq);

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("data", memberInfo);

      sendJsonResponse(responseMap, response, gson);
    } catch (Exception e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
    }
  }
}
