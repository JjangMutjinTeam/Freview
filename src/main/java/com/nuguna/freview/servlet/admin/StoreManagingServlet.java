package com.nuguna.freview.servlet.admin;

import com.nuguna.freview.dao.admin.AdminDAO;
import com.nuguna.freview.dto.StoreAndBoss;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminPage/store")
public class StoreManagingServlet extends HttpServlet {

  AdminDAO adminDAO = new AdminDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");
    List<StoreAndBoss> storeAndBossList = adminDAO.selectStoreBusinessInfo();
    System.out.println(storeAndBossList);
    req.setAttribute("storeAndBossList", storeAndBossList);
    RequestDispatcher rd = req.getRequestDispatcher("/admin-mg-stores-y.jsp");
    rd.forward(req, resp);
  }
}
