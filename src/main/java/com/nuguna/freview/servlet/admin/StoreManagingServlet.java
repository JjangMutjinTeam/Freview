package com.nuguna.freview.servlet.admin;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndText;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.dto.StoreAndBossDTO;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/admin-store-management")
public class StoreManagingServlet extends HttpServlet {

  private AdminDAO adminDAO = new AdminDAO();
  private Gson gson = new Gson();

  private final int LIMIT = 30;
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndText(request, response);

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("Member");

    request.setAttribute("loginUser", loginUser);

    request.getRequestDispatcher("/admin-management-store-y.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    String previousStoreNumber = getPreviousBusinessNumber(request);
    List<StoreAndBossDTO> storeAndBossList = loadStoreList(previousStoreNumber);
    boolean hasMore = storeAndBossList.size() == LIMIT;
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("data", storeAndBossList);
    responseMap.put("hasMore", hasMore);

    sendJsonResponse(responseMap, response, gson);
  }

  private String getPreviousBusinessNumber(HttpServletRequest request) {
    String previousBusinessNumber = "999-99-99999";
    if (request.getParameter("previousBusinessNumber") != null) {
      try {
        previousBusinessNumber = request.getParameter("previousBusinessNumber");
      } catch (NumberFormatException e) {
        previousBusinessNumber = "999-99-99999";
      }
    }
    return previousBusinessNumber;
  }

  private List<StoreAndBossDTO> loadStoreList(String previousStoreNumber) {
    return adminDAO.getStoreBusinessInfo(previousStoreNumber, LIMIT);
  }
}
