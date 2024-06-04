package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.RegisterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String pageCode = req.getParameter("pagecode"); // pagecode 받기

    RegisterDAO rdao = new RegisterDAO();

    if(pageCode.equals("register")){ // 회원가입 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("COMM_register.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("findid")){ // 아이디찾기 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("COMM_FINDID.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("findpw")){ // 비밀번호 찾기 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("COMM_FINDPW.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("checkID")){ // 아이디중복확인
      String id = req.getParameter("id");
      int result = rdao.getDuplicationResultByID(id);
      PrintWriter out = resp.getWriter();
      out.print(result);
    }


  }

  @Override
  public void destroy() {
  }
}
