package com.mystatusonline.webapp.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mystatusonline.model.Person;
import com.mystatusonline.service.PersonService;
import com.oreilly.servlet.Base64Decoder;
import com.oreilly.servlet.Base64Encoder;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 24, 2010 Time: 12:42:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CookieUtil {

  public static void saveCookies(HttpServletResponse response, String username, String password) {
    Cookie usernameCookie = new Cookie("msou",
                                       Base64Encoder.encode(username));
    usernameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 day expiration
    response.addCookie(usernameCookie);
    Cookie passwordCookie = new Cookie("msop",
                                       Base64Encoder.encode(password));
    passwordCookie.setMaxAge(60 * 60 * 24 * 30); // 30 day expiration
    response.addCookie(passwordCookie);
  }

  public static void removeCookies(HttpServletResponse response) {
    // expire the username cookie by setting maxAge to 0
    // (actual cookie value is irrelevant)
    Cookie unameCookie = new Cookie("msou", "expired");
    unameCookie.setMaxAge(0);
    response.addCookie(unameCookie);

    // expire the password cookie by setting maxAge to 0
    // (actual cookie value is irrelevant)
    Cookie pwdCookie = new Cookie("msop", "expired");
    pwdCookie.setMaxAge(0);
    response.addCookie(pwdCookie);
  }

  public static void checkLogin(HttpServletRequest request,HttpServletResponse response,
                                PersonService personManager) {
    Cookie[] cookies = request.getCookies();
    String username = null;
    String password = null;
    for (int i = 0; i < cookies.length; i++) {
      Cookie cookie = cookies[i];
      if (cookie.getName().equals("msou")){
        username = Base64Decoder.decode(cookie.getValue());
      } else if (cookie.getName().equals("msop")){
        password = Base64Decoder.decode(cookie.getValue());
      }
    }
    if (username != null && password != null){
      Person person = null;
      try {
        person = personManager.login(username,password);
        SessionUtil.saveLoggedInPerson(person,request.getSession());
      } catch (Exception e) {
        removeCookies(response);
      }
    }      
  }
}
