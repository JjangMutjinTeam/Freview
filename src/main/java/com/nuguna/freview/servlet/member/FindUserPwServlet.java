package com.nuguna.freview.servlet.member;

import com.nuguna.freview.dao.member.RegisterDAO;
import com.nuguna.freview.util.SendMailUtil;
import com.nuguna.freview.util.ShaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/findpw")
public class FindUserPwServlet extends HttpServlet{
  @Override
  public void init() throws ServletException {
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


    String userEmail = req.getParameter("findpwEmail");
    String userID = req.getParameter("findpwId");

    System.out.println(userEmail);
    System.out.println(userID);

    RegisterDAO rdao = new RegisterDAO();
    int check = rdao.getCheckMemberByEmailAndID(userEmail,userID);

    System.out.println(check);

    if(check==1){
      List<String> list = new ArrayList<String>();
      Random random = new Random();
      for(int i=0;i<=4;i++){
        list.add(random.nextInt(10)+"");
      }
      for(int i=0;i<=4;i++){
        list.add(String.valueOf((char)(random.nextInt(26)+65)));
      }
      Collections.shuffle(list);
      StringBuffer changedPw = new StringBuffer();
      for(String s : list){
        changedPw.append(s);
      }

      String title = "[FReview] 비밀번호 재생성 확인";
      String content = "재생성된 비밀번호는 "+changedPw+" 입니다";

      SendMailUtil mail = new SendMailUtil();
      mail.goMail(mail.setting(new Properties()),title,content,userEmail);

      String shaPw = new ShaUtil().sha256Encodeing(changedPw.toString());
      rdao.updateUserPW(shaPw,userID);

      PrintWriter out = resp.getWriter();
      out.print(check);
    }else{
      PrintWriter out = resp.getWriter();
      out.print(check);
    }



  }

  @Override
  public void destroy() {
  }
}
