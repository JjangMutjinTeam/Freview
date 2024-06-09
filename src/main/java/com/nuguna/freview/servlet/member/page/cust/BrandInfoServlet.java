package com.nuguna.freview.servlet.member.page.cust;

import com.nuguna.freview.dao.member.common.MemberUtilDAO;
import com.nuguna.freview.dao.member.cust.page.CustMyBrandInfoDAO;
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
@WebServlet("/my-info")
public class BrandInfoServlet extends HttpServlet {

  private MemberUtilDAO memberUtilDAO;
  private CustMyBrandInfoDAO custMyBrandInfoDAO;

  @Override
  public void init() throws ServletException {
    log.info("BrandInfoServlet 초기화");
    memberUtilDAO = new MemberUtilDAO();
    custMyBrandInfoDAO = new CustMyBrandInfoDAO();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      int memberSeq = Integer.parseInt(request.getParameter("member_seq"));

      MemberGubun memberGubun = memberUtilDAO.selectMemberGubun(memberSeq);
      if (memberGubun == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
            "유효하지 않은 유저입니다.");
      }

      if (memberGubun.isBoss()) {

      } else if (memberGubun.isCust()) {
        CustMyBrandInfoDto brandInfo = custMyBrandInfoDAO.getCustBrandInfo(memberSeq);
        log.info("brandInfo = " + brandInfo);
        request.setAttribute("member_seq", memberSeq);
//        request.setAttribute("")
        request.setAttribute("brandInfo", brandInfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cust-my-brand-info.jsp");
        dispatcher.forward(request, response);
      }


    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          e.getMessage());
    }

  }
}
