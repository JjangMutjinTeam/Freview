package com.nuguna.freview.servlet.admin;

import com.nuguna.freview.dao.admin.MainPageDAO;
import com.nuguna.freview.dto.MainpageGongjiDTO;
import com.nuguna.freview.dto.MainpageMemberInfoDTO;
import com.nuguna.freview.dto.MainpageMojipDTO;
import com.nuguna.freview.dto.MainpageRequesterDTO;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.post.Post;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
  @Override
  public void init() throws ServletException {
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    MainPageDAO mdao = new MainPageDAO();

    String pageCode = req.getParameter("pagecode");
    if(pageCode.equals("non_member")){ // 비회원 메인페이지로 이동
      ArrayList<MainpageMojipDTO> mojipPost = mdao.getMojipPostforMainPage();
      ArrayList<MainpageRequesterDTO> requesters = mdao.getRequestersForMainPage();
      ArrayList<MainpageGongjiDTO> gongji = mdao.getGongjisForMainPage();

      req.setAttribute("mojips", mojipPost);
      req.setAttribute("requesters", requesters);
      req.setAttribute("gongji", gongji);

      req.getRequestDispatcher("common-main-nonmamber.jsp").forward(req, resp);
    }
    else if(pageCode.equals("Boss")){
      int seq = Integer.parseInt(req.getParameter("seq"));
      MainpageMemberInfoDTO minfodto = mdao.getMemberInfoForMainPageHeader(seq);
      ArrayList<MainpageRequesterDTO> requesters = mdao.getRequestersForMainPage();
      ArrayList<MainpageGongjiDTO> gongji = mdao.getGongjisForMainPage();

      req.setAttribute("memberInfo",minfodto);
      req.setAttribute("requesters", requesters);
      req.setAttribute("gongji", gongji);

      req.getRequestDispatcher("common-boss-main.jsp").forward(req, resp);
    }
    else if(pageCode.equals("Requester")){
      int seq = Integer.parseInt(req.getParameter("seq"));
      MainpageMemberInfoDTO minfodto = mdao.getMemberInfoForMainPageHeader(seq);
      ArrayList<MainpageMojipDTO> mojipPost = mdao.getMojipPostforMainPage();
      ArrayList<MainpageGongjiDTO> gongji = mdao.getGongjisForMainPage();

      req.setAttribute("memberInfo",minfodto);
      req.setAttribute("mojips", mojipPost);
      req.setAttribute("gongji", gongji);

      req.getRequestDispatcher("common-reviewer-main.jsp").forward(req, resp);
    }
  }

  @Override
  public void destroy() {
  }
}
