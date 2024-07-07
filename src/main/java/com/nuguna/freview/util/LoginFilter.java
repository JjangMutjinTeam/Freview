package com.nuguna.freview.util;

import com.nuguna.freview.entity.member.Member;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

  private List<String> excludeUrls;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
    String excludeUrlsStr = filterConfig.getInitParameter("excludeUrls");
    excludeUrls = Arrays.asList(excludeUrlsStr.split(","));
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    String path = ((HttpServletRequest) servletRequest).getRequestURI();
    System.out.println(path);
    if (!excludeUrls.contains(path)) {
      System.out.println("doFilter");
      HttpSession session = ((HttpServletRequest) servletRequest).getSession();
      Member member = (Member) session.getAttribute("Member");
      System.out.println(member);
      if(member == null){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String cPath = request.getContextPath();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendRedirect(cPath+"/auth?pagecode=login");
        return;
      }
    }

      filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
