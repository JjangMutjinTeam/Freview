package com.nuguna.freview.servlet.admin;

import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.entity.admin.StoreBusinessInfo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminPage/addStore")
public class StoreAddServlet extends HttpServlet {

  AdminDAO adminDAO = new AdminDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("application/json; charset=UTF-8");
    StoreBusinessInfo storeBusinessInfo = new StoreBusinessInfo();
    storeBusinessInfo.setBusinessNumber(req.getParameter("addBusinessNumber"));
    storeBusinessInfo.setStoreName(req.getParameter("addStoreName"));
    boolean insertStore = adminDAO.insertStore(storeBusinessInfo);

    if (insertStore) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
