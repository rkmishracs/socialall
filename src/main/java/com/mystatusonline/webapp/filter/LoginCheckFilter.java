package com.mystatusonline.webapp.filter;

import java.io.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import com.mystatusonline.webapp.util.SessionUtil;


/**
 * Filter to wrap request with a request including user preferred locale.
 */
public class LoginCheckFilter
  extends OncePerRequestFilter {

  /**
   * This method looks for a "locale" request parameter. If it finds one, it
   * sets it as the preferred locale and also configures it to work with
   * JSTL.
   *
   * @param request the current request
   * @param response the current response
   * @param chain the chain
   * @throws java.io.IOException when something goes wrong
   * @throws javax.servlet.ServletException when a communication failure happens
   */
  @SuppressWarnings("unchecked")
  public void doFilterInternal(HttpServletRequest request,
                               HttpServletResponse response,
                               FilterChain chain)
    throws IOException, ServletException {


    HttpSession session = request.getSession(false);
    if (request.getRequestURI().toLowerCase().contains("sitelogin.html")
      || request.getRequestURI().toLowerCase().contains("attemplogin.html")
      || request.getRequestURI().toLowerCase().contains("signup.html")){
      chain.doFilter(request,response);
    }

    if (session != null) {
      if (!SessionUtil.isPersonLoggedIn(request.getSession())){
        response.sendRedirect("siteLogin.html");
      }
    }
    chain.doFilter(request, response);
  }
/*
  public void init(FilterConfig filterConfig)
    throws ServletException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain)
    throws IOException, ServletException {
    *//*HttpSession session = request.getSession(false);
    if (request.getRequestURI().toLowerCase().contains("sitelogin.html")){
      chain.doFilter(request,response);
    }

    if (session != null) {
      if (session.getAttribute("isLoggedIn") == null){
        response.sendRedirect("siteLogin.html");
      }
    }*//*
    chain.doFilter(request, response);
  }

  public void destroy() {
    //To change body of implemented methods use File | Settings | File Templates.
  }*/
}