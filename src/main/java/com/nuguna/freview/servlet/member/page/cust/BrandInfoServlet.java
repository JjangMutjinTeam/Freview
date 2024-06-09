package com.nuguna.freview.servlet.member.page.cust;

import com.nuguna.freview.dao.member.boss.page.BossMyBrandInfoDAO;
import com.nuguna.freview.dao.member.common.MemberUtilDAO;
import com.nuguna.freview.dao.member.cust.page.CustMyBrandInfoDAO;
import com.nuguna.freview.dto.boss.brand.BossMyBrandInfoDto;
import com.nuguna.freview.dto.cust.brand.CustMyBrandInfoDto;
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
@WebServlet("/brand-page")
public class BrandInfoServlet extends HttpServlet {

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

      int memberSeq = Integer.parseInt(request.getParameter("member_seq"));
      int fromMemberSeq = Integer.parseInt(
          request.getParameter("from_member_seq")); // from_member_seq = 세션으로 가져오면 됨.

      MemberGubun memberGubun = memberUtilDAO.selectMemberGubun(memberSeq);
      if (memberGubun == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "유효하지 않은 유저에 대한 요청입니다.");
      }

      MemberGubun memberGubunFrom = memberUtilDAO.selectMemberGubun(fromMemberSeq);
      if (memberGubunFrom == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "유효하지 않은 유저에 대한 요청입니다.");
      }

      String fromMemberNickname = memberUtilDAO.selectMemberNickname(fromMemberSeq);

      if (memberGubun.isBoss()) {
        BossMyBrandInfoDto brandInfo = bossMyBrandInfoDAO.getBossBrandInfo(memberSeq);
        log.info("brandInfo = " + brandInfo);
        request.setAttribute("member_seq", memberSeq);
        request.setAttribute("brandInfo", brandInfo);
        if (memberGubunFrom.isCust()) {
          request.setAttribute("fromMemberNickname", fromMemberNickname);
        } else if (memberGubun.isBoss()) {
          request.setAttribute("fromMemberNickname", fromMemberNickname);
        } else {
          request.setAttribute("fromMemberNickname", "어드민");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/boss-brand-info.jsp");
        dispatcher.forward(request, response);
      } else if (memberGubun.isCust()) {
        CustMyBrandInfoDto brandInfo = custMyBrandInfoDAO.getCustBrandInfo(memberSeq);
        log.info("brandInfo = " + brandInfo);
        request.setAttribute("member_seq", memberSeq);
        request.setAttribute("brandInfo", brandInfo);
        request.setAttribute("fromMemberNickname", fromMemberNickname);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cust-brand-info.jsp");
        dispatcher.forward(request, response);
      }
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          e.getMessage());
    }

  }
}
