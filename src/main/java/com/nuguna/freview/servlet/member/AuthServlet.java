package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.LoginDAO;
import com.nuguna.freview.dao.member.RegisterDAO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.util.ShaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

  private ShaUtil sha;
  @Override
  public void init() throws ServletException {
    sha = new ShaUtil();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("utf-8");

    String pageCode = req.getParameter("pagecode"); // pagecode 받기

    RegisterDAO rdao = new RegisterDAO();
    LoginDAO ldao = new LoginDAO();

    if(pageCode.equals("register")){ // 회원가입 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("common-register.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("login")){ // 로그인 페이지로 이동
      RequestDispatcher rd = req.getRequestDispatcher("common-login.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("findid")){ // 아이디찾기 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("common-findid.jsp");
      rd.forward(req, resp);
    }

    else if(pageCode.equals("findpw")){ // 비밀번호 찾기 페이지 이동
      RequestDispatcher rd = req.getRequestDispatcher("common-findpw.jsp");
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
      String purePassword = req.getParameter("password");
      String hashPassword = sha.sha256Encoding(purePassword);
      String email = req.getParameter("email");
      String nickname = req.getParameter("nickname");
      String agegroup = req.getParameter("agegroup");

      int insertRow = rdao.insertReviewer(id,hashPassword,email,nickname,agegroup);
      if(insertRow==1){
        RequestDispatcher rd = req.getRequestDispatcher("common-register-check.jsp");
        rd.forward(req,resp);
      }else{
        RequestDispatcher rd = req.getRequestDispatcher("common-register-fail.jsp");
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
      String sha_password = sha.sha256Encoding(password);
      String email = req.getParameter("email");
      String agegroup = req.getParameter("agegroup");
      String buisness_number = req.getParameter("buisness_number");
      String store_loc = req.getParameter("store_loc");
      String nickname =req.getParameter("nickname");


      int insertRow = rdao.insertBoss(id,sha_password,nickname,email,buisness_number,agegroup,store_loc);
      if(insertRow==1){
        RequestDispatcher rd = req.getRequestDispatcher("common-register-check.jsp");
        rd.forward(req,resp);
      }else{
        RequestDispatcher rd = req.getRequestDispatcher("common-register-fail.jsp");
        rd.forward(req,resp);
      }
    }

    else if(pageCode.equals("login_check")){ // 로그인

      String id = req.getParameter("id");
      String password = req.getParameter("password");
      String sha_password = sha.sha256Encoding(password);
      Member user = ldao.getMemberByIdPw(id,sha_password);
      HttpSession session = req.getSession();
      session.setAttribute("Member",user);
      System.out.println(user);

        if(user==null){
          RequestDispatcher rd = req.getRequestDispatcher("common-login-fail.jsp");
          rd.forward(req,resp);
        }
        else if(user.getGubun().equals("B")){
          RequestDispatcher rd = req.getRequestDispatcher("common-boss-main.jsp");
          rd.forward(req,resp);
        } else if(user.getGubun().equals("C")){
          RequestDispatcher rd = req.getRequestDispatcher("common-reviewer-main.jsp");
          rd.forward(req,resp);
        }
        else if(user.getGubun().equals("A")){
          System.out.println("user : " + user);
          resp.sendRedirect(req.getContextPath() + "/admin");
        }

    }

    else if(pageCode.equals("checkduplocatedNumberinmember")){
      String buisnessInfo = req.getParameter("buisnessInfo");
      int  result = rdao.getCheckDuplicatedInMember(buisnessInfo);
      PrintWriter out = resp.getWriter();
      out.print(result);
    }

    else if(pageCode.equals("findId")){
      String email = req.getParameter("findidEmail");
      String id = rdao.getFindIdByEmail(email);
      System.out.println("servlet: "+id);
      PrintWriter out = resp.getWriter();
      out.print(id);
    }

  }

  @Override
  public void destroy() {
  }
}
