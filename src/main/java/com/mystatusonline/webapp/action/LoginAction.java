package com.mystatusonline.webapp.action;


import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.mystatusonline.model.Person;
import com.mystatusonline.webapp.util.SessionUtil;
import com.mystatusonline.webapp.util.CookieUtil;


/**
 * Action to allow new users to sign up.
 */
public class LoginAction
  extends BaseAction {

  private static final long serialVersionUID = 6558317334878272308L;
/*  private Person person;*/
  private String cancel;
  private String login;

  private String username;
  private String password;

  private boolean rememberMe;

  public boolean isRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(boolean rememberMe) {
    this.rememberMe = rememberMe;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCancel() {
    return cancel;
  }

  public void setCancel(String cancel) {
    this.cancel = cancel;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
/*
  public void setPerson(Person person) {
    this.person = person;
  }

  *//**
   * Return an instance of the user - to display when validation errors occur
   *
   * @return a populated user
   *//*
  public Person getPerson() {
    return person;
  }*/

  /**
   * When method=GET, "input" is returned. Otherwise, "success" is returned.
   *
   * @return cancel, input or success
   */
  public String execute() {
    if (cancel != null) {
      return CANCEL;
    }
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {
      if (SessionUtil
        .isPersonLoggedIn(ServletActionContext.getRequest().getSession())) {
        return LOGGED_IN;
      }
      return INPUT;
    }
    return SUCCESS;
  }

  /**
   * Returns "input"
   *
   * @return "input" by default
   */
  public String doDefault() {
    return INPUT;
  }

  /**
   * Save the user, encrypting their passwords if necessary
   *
   * @return success when good things happen
   * @throws Exception when bad things happen
   */
  public String login()
    throws Exception {
    Person loggedInPerson = null;
    try {
      loggedInPerson = personManager.login(getUsername(), getPassword());
    } catch (DataAccessException e) {
      addActionError(getText("person.notfound"));
      return ERROR;
    }
    if (loggedInPerson == null) {
      addActionError(getText("person.notfound"));
      return ERROR;
    }
    SessionUtil.saveLoggedInPerson(loggedInPerson,
                                   ServletActionContext.getRequest().getSession(
                                     true));
    if (rememberMe){
      CookieUtil.saveCookies(ServletActionContext.getResponse(),getUsername(),getPassword());
    }

    if (!loggedInPerson.isAnySocialNetworkInitialized()){
      return SOCIAL;
    }

    return SUCCESS;
  }
}