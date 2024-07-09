package com.nuguna.freview.servlet.admin;

import static com.nuguna.freview.util.EncodingUtil.setEncodingToUTF8AndJson;

import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.entity.admin.StoreBusinessInfo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin-store-add")
public class StoreAddServlet extends HttpServlet {

  AdminDAO adminDAO = new AdminDAO();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    setEncodingToUTF8AndJson(request, response);

    StoreBusinessInfo storeBusinessInfo = new StoreBusinessInfo();
    storeBusinessInfo.setBusinessNumber(request.getParameter("addBusinessNumber"));
    storeBusinessInfo.setStoreName(request.getParameter("addStoreName"));
    boolean insertStore = adminDAO.insertStore(storeBusinessInfo);

    if (insertStore) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
