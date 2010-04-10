package com.mystatusonline.webapp.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.mystatusonline.service.PersonNotLoggedInException;
import com.mystatusonline.service.PersonService;
import com.mystatusonline.webapp.util.SessionUtil;
import com.mystatusonline.webapp.util.CookieUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 15, 2010 Time: 3:52:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginCheckInterceptor extends AbstractInterceptor {

  private PersonService personManager;

  public String intercept(ActionInvocation invocation) throws Exception {
    HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
    HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(
      StrutsStatics.HTTP_RESPONSE);

    if(!SessionUtil.isPersonLoggedIn(request.getSession())) {
      CookieUtil.checkLogin(request,response,personManager);
    }

		Map session = invocation.getInvocationContext().getSession();

		if(!SessionUtil.isPersonLoggedIn(session)) {
			addActionError(invocation, "You must be logged in to access this page");
			throw new PersonNotLoggedInException();
		}

		return invocation.invoke();
	}

	private void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}

  public void setPersonManager(PersonService personManager) {
    this.personManager = personManager;
  }

  public PersonService getPersonManager() {
    return personManager;
  }
}