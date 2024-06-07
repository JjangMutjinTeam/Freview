package com.nuguna.freview.servlet.member.page;

import com.nuguna.freview.dao.member.BossMyBrandDAO;
import com.nuguna.freview.entity.member.Member;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/BrandInfoServlet")
public class BrandInfoServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    BossMyBrandDAO dao = new BossMyBrandDAO();
    ArrayList<Member> amb = dao.getBrand();
    System.out.println(amb.size());

    System.out.println("=================");

//    PrintWriter out = response.getWriter();
    System.out.println("여기다!");
    request.getSession().setAttribute("KEY_BOSSBRAND", amb);
    System.out.println("멤버" + amb.toString());
    request.getRequestDispatcher("boss-my-brand.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }
}
