package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.LoginDAO;
import com.nuguna.freview.dao.member.RegisterDAO;
import com.nuguna.freview.entity.member.Member;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("utf-8");

    String pageCode = req.getParameter("pagecode"); // pagecode 받기

    RegisterDAO rdao = new RegisterDAO();
    LoginDAO ldao = new LoginDAO();

    if(pageCode.equals("register")){ // 회원가입 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("COMM_register.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("login")){ // 로그인 페이지로 이동
      RequestDispatcher rd = req.getRequestDispatcher("COMM_LGN.jsp");
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
      System.out.println(id);
      int result = rdao.getDuplicationResultByID(id);
      PrintWriter out = resp.getWriter();
      out.print(result);
    }

    else if(pageCode.equals("checkNickName")){ // 닉네임중복확인
      String nickName = req.getParameter("NickName");
      int result = rdao.getDuplicationResultByNickName(nickName);
      PrintWriter out = resp.getWriter();
      out.print(result);
    }

    else if(pageCode.equals("reviewer_regist")){ // 체험단 회원가입
      System.out.println("서블릿으로 이동");

      String id = req.getParameter("id");
      String password = req.getParameter("password");
      String email = req.getParameter("email");
      String nickname = req.getParameter("nickname");
      String agegroup = req.getParameter("agegroup");

      int insertRow = rdao.registReviewer(id,password,email,nickname,agegroup);
      if(insertRow==1){
        RequestDispatcher rd = req.getRequestDispatcher("COMM_register_check.jsp");
        rd.forward(req,resp);
      }else{
        RequestDispatcher rd = req.getRequestDispatcher("COMM_register_fail.jsp");
        rd.forward(req,resp);
      }
    }

    else if(pageCode.equals("checkbuisnessInfo")){ // 사업자등록번호 확인하기
      String businessInfo = req.getParameter("buisnessInfo");
      int result = rdao.getCheckCollectBuisnessInfo(businessInfo);
      PrintWriter out = resp.getWriter();
      out.print(result);
    }

    else if(pageCode.equals("Boss_regist")){ // 사장님 회원가입
      String id = req.getParameter("id");
      String password = req.getParameter("password");
      String email = req.getParameter("email");
      String agegroup = req.getParameter("agegroup");
      String buisness_number = req.getParameter("buisness_number");
      String store_loc = req.getParameter("store_loc");
      String nickname =req.getParameter("nickname");


      int insertRow = rdao.registBoss(id,password,nickname,email,buisness_number,agegroup,store_loc);
      if(insertRow==1){
        RequestDispatcher rd = req.getRequestDispatcher("COMM_register_check.jsp");
        rd.forward(req,resp);
      }else{
        RequestDispatcher rd = req.getRequestDispatcher("COMM_register_fail.jsp");
        rd.forward(req,resp);
      }
    }

    else if(pageCode.equals("login_check")){
      String id = req.getParameter("id");
      String password = req.getParameter("password");
      Member user = ldao.getMemberByIdPw(id,password);
      

      System.out.println(user);

        if(user==null){
          RequestDispatcher rd = req.getRequestDispatcher("COMM_LGN_LoginFail.jsp");
          rd.forward(req,resp);
        }
        else if(user.getGubun().equals("B")){
          HttpSession session = req.getSession();
          session.setAttribute("BOSS",user);
          RequestDispatcher rd = req.getRequestDispatcher("COMM_BOSSmain.jsp");
          rd.forward(req,resp);
        } else if(user.getGubun().equals("C")){
          HttpSession session = req.getSession();
          session.setAttribute("REVIEWER",user);
          RequestDispatcher rd = req.getRequestDispatcher("COMM_REVIEWERmain.jsp");
          rd.forward(req,resp);
        }

    }

    else if(pageCode.equals("checkduplocatedNumberinmember")){
      String buisnessInfo = req.getParameter("buisnessInfo");
      int  result = rdao.getCheckDuplicatedInMember(buisnessInfo);
      PrintWriter out = resp.getWriter();
      out.print(result);
    }

  }

  @Override
  public void destroy() {
  }
}
