package com.mystatusonline.webapp.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.mystatusonline.service.EmailExistsException;
import com.mystatusonline.service.PhoneNumberExistsException;
import com.mystatusonline.service.UsernameExistsException;
import org.apache.struts2.ServletActionContext;
import org.appfuse.Constants;
import org.appfuse.model.User;
import org.springframework.mail.MailException;
import org.springframework.security.AccessDeniedException;

import net.tanesha.recaptcha.ReCaptchaResponse;

import com.mystatusonline.model.Person;
import com.mystatusonline.service.PersonExistsException;
import com.mystatusonline.webapp.util.RecaptchaHelper;
import com.mystatusonline.webapp.util.RequestUtil;


/**
 * Action to allow new users to sign up.
 */
public class SignupAction
        extends BaseAction {

    private static final long serialVersionUID = 6558317334878272308L;
    private Person person;
    private User user;
    private String cancel;

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Return an instance of the user - to display when validation errors occur
     *
     * @return a populated user
     */
    public Person getPerson() {
        return person;
    }

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
    public String save()
            throws Exception {

        ReCaptchaResponse captchaResponse = RecaptchaHelper.validateResponse(ServletActionContext.getRequest());
        if (!captchaResponse.isValid()) {
            addActionError(getText("human.failed"));
            return INPUT;
        }
        try {
            personManager.savePerson(person);
        } catch (AccessDeniedException ade) {
            log.warn(ade.getMessage());
            getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
            return INPUT;
        } catch (UsernameExistsException e) {
            log.warn(e.getMessage());
            List<Object> args = new ArrayList<Object>();
            args.add(person.getUsername());
            addActionError(getText("errors.existing.username", args));
            person.setPassword(person.getConfirmPassword());
            return INPUT;
        } catch (EmailExistsException e) {
            log.warn(e.getMessage());
            List<Object> args = new ArrayList<Object>();
            args.add(person.getEmail());
            addActionError(getText("errors.existing.email", args));
            person.setPassword(person.getConfirmPassword());
            return INPUT;
        } catch (PhoneNumberExistsException e) {
            log.warn(e.getMessage());
            List<Object> args = new ArrayList<Object>();
            args.add(person.getEmail());
            addActionError(getText("errors.existing.phone", args));
            person.setPassword(person.getConfirmPassword());
            return INPUT;
        }

        saveMessage(getText("user.registered"));
        getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        // Send an account information e-mail
        mailMessage.setSubject(getText("signup.email.subject"));

        try {
            sendPersonMessage(person, getText("signup.email.message"),
                    RequestUtil.getAppURL(getRequest()), new HashMap<String, Object>());
        } catch (MailException me) {
            addActionError(me.getMostSpecificCause().getMessage());
        }

        return SUCCESS;
    }
}
