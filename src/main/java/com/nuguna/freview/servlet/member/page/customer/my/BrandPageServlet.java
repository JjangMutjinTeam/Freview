package com.nuguna.freview.servlet.member.page.customer.my;

import com.nuguna.freview.dao.member.boss.page.BossMyBrandInfoDAO;
import com.nuguna.freview.dao.member.common.MemberUtilDAO;
import com.nuguna.freview.dao.member.cust.page.CustMyBrandInfoDAO;
import com.nuguna.freview.dto.boss.brand.BossMyBrandInfoDto;
import com.nuguna.freview.dto.cust.brand.CustMyBrandInfoDto;
import com.nuguna.freview.entity.member.Member;
import com.nuguna.freview.entity.member.MemberGubun;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet("/my-info")
public class BrandPageServlet extends HttpServlet {

  private MemberUtilDAO memberUtilDAO;
  private BossMyBrandInfoDAO bossMyBrandInfoDAO;
  private CustMyBrandInfoDAO custMyBrandInfoDAO;


  @Override
  public void init() throws ServletException {
    log.info("BrandInfoServlet 초기화");
    memberUtilDAO = new MemberUtilDAO();
    bossMyBrandInfoDAO = new BossMyBrandInfoDAO();
    custMyBrandInfoDAO = new CustMyBrandInfoDAO();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      Member member = (Member) request.getSession().getAttribute("Member");
      int memberSeq = member.getMemberSeq();
      MemberGubun memberGubun = memberUtilDAO.selectMemberGubun(memberSeq);
      if (memberGubun == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "유효하지 않은 유저입니다.");
        return;
      }

      request.setAttribute("member_seq", memberSeq);
      if (memberGubun.isBoss()) {
        BossMyBrandInfoDto brandInfo = bossMyBrandInfoDAO.getBossBrandInfo(memberSeq);
        log.info("bossBrandInfo = " + brandInfo);
        request.setAttribute("brandInfo", brandInfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/boss-my-brand-info.jsp");
        dispatcher.forward(request, response);
      } else if (memberGubun.isCust()) {
        CustMyBrandInfoDto brandInfo = custMyBrandInfoDAO.getCustBrandInfo(memberSeq);
        log.info("brandInfo = " + brandInfo);
        request.setAttribute("brandInfo", brandInfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/customer-my-brand-info.jsp");
        dispatcher.forward(request, response);
      }


    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          e.getMessage());
    }

  }
}
