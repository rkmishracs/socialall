package com.mystatusonline.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.struts2.ServletActionContext;
import org.springframework.mail.MailException;

import net.tanesha.recaptcha.ReCaptchaResponse;

import com.mystatusonline.model.Person;
import com.mystatusonline.service.PersonNotExistsException;
import com.mystatusonline.webapp.util.RequestUtil;
import com.mystatusonline.webapp.util.RecaptchaHelper;


/**
 * Action class to send password hints to registered users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class PasswordHintAction
  extends BaseAction {

  private static final long serialVersionUID = -4037514607101222025L;
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Execute sending the password hint via e-mail.
   *
   * @return success if username works, input if not
   */
  public String execute() {
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {
      return INPUT;
    }
    return SUCCESS;
  }


  public String resetPassword() {

    ReCaptchaResponse captchaResponse = RecaptchaHelper.validateResponse(ServletActionContext.getRequest());
    if (!captchaResponse.isValid()){
      addActionError(getText("human.failed"));
      return INPUT;
    }


    List<Object> args = new ArrayList<Object>();

    // ensure that the username has been sent
    if (email == null) {
      log.warn(
        "Email not specified, notifying user that it's a required field.");

      args.add(getText("person.email"));
      addActionError(getText("errors.requiredField", args));
      return INPUT;
    }

    if (log.isDebugEnabled()) {
      log.debug("Processing Password Hint...");
    }

    // look up the user's information
    try {
      List<Person> persons = personManager.findByEmail(email);
      Person person = persons.get(0);
      String resetToken = personManager.resetPasswordToken(person.getId());

      String resetUrl = "token="+resetToken+"&personId="+person.getId();
      Map<String,Object> ctx = new HashMap<String, Object>();
      ctx.put("resetURL",RequestUtil.getAppURL(getRequest())+"/resetPasswordNow.html?"+resetUrl);

      try {
        mailMessage.setSubject(getText("signup.email.resetSubject"));
        sendPersonMessage(person, getText("signup.email.resetMessage"),
                          RequestUtil.getAppURL(getRequest()),ctx);
      } catch (MailException me) {
        addActionError(me.getMostSpecificCause().getMessage());
      }
    } catch (PersonNotExistsException e) {
      log.warn(e.getMessage());
      args.add(email);
      addActionError(getText("login.resetpassword.error", args));
      getSession().setAttribute("errors", getActionErrors());
      return INPUT;
    } catch (MailException me) {
      addActionError(me.getCause().getLocalizedMessage());
      getSession().setAttribute("errors", getActionErrors());
      return INPUT;
    }
    saveMessage(getText("resetPassword.message"));
    return SUCCESS;
  }
}
