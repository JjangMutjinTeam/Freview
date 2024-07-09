package com.nuguna.freview.servlet.admin;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;
import static com.nuguna.freview.util.JsonResponseUtil.sendJsonResponse;

import com.google.gson.Gson;
import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.dto.StoreAndBossDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin-store-search")
public class StoreSearchServlet extends HttpServlet {
  private AdminDAO adminDAO = new AdminDAO();
  private Gson gson = new Gson();

  private final int LIMIT = 30;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    String previousStoreNumber = getPreviousBusinessNumber(request);
    String searchWord = request.getParameter("searchWord");
    List<StoreAndBossDTO> storeAndBossList = loadStoreList(previousStoreNumber, searchWord);
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

  private List<StoreAndBossDTO> loadStoreList(String previousStoreNumber, String searchWord) {
    return adminDAO.getStoreBusinessInfo(previousStoreNumber, LIMIT, searchWord);
  }
}
