package com.nuguna.freview.servlet.member;

import com.nuguna.freview.util.SendMailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet("/send-certification")
public class SendCertificationToEmailServlet extends HttpServlet{
  @Override
  public void init() throws ServletException {
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


    String ToEmail = req.getParameter("email");
    String randomNumber = req.getParameter("randomNumber");

    String title = "[FReview] 이메일 인증번호 확인";
    String content = "인증번호는 "+randomNumber +" 입니다";

    SendMailUtil mail = new SendMailUtil();
    mail.goMail(mail.setting(new Properties()),title,content,ToEmail);

    PrintWriter out = resp.getWriter();
    out.print(randomNumber);

  }

  @Override
  public void destroy() {
  }
}
