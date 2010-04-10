package com.mystatusonline.webapp.action;

import org.apache.struts2.ServletActionContext;

import com.mystatusonline.service.InvalidPasswordResetTokenException;


/**
 * Action class to send password hints to registered users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class PasswordResetNowAction
  extends BaseAction {

  private static final long serialVersionUID = -4037514607101222025L;
  /*private Person person;


  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }*/

  private String password;
  private String confirmPassword;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  /**
   * Execute sending the password hint via e-mail.
   *
   * @return success if username works, input if not
   */
  public String execute() {
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {
      String token = ServletActionContext.getRequest().getParameter("token");
      Long personId = Long.parseLong(ServletActionContext.getRequest().getParameter("personId"));
      ServletActionContext.getRequest().getSession().setAttribute("token",token);
      ServletActionContext.getRequest().getSession().setAttribute("personId",personId);
      return INPUT;
    }
    return SUCCESS;
  }


  public String resetPassword() {
    try {
      String token = (String)ServletActionContext.getRequest().getSession().getAttribute("token");
      Long personId = (Long)ServletActionContext.getRequest().getSession().getAttribute("personId");

      personManager.resetPassword(token, personId, getPassword());
    } catch (InvalidPasswordResetTokenException e) {
      saveMessage(getText("resetPassword.error"));
      return ERROR;
    }
    saveMessage(getText("resetPassword.success"));
    return SUCCESS;

  }
}