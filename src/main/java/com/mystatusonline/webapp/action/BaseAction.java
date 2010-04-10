package com.mystatusonline.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.appfuse.Constants;
import org.appfuse.service.MailEngine;
import org.springframework.mail.SimpleMailMessage;

import com.mystatusonline.model.Person;
import com.mystatusonline.service.PersonManager;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Implementation of <strong>ActionSupport</strong> that contains convenience
 * methods for subclasses.  For example, getting the current user and saving
 * messages/errors. This class is intended to be a base class for all Action
 * classes.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseAction
  extends ActionSupport {

  private static final long serialVersionUID = 3525445612504421307L;

  /**
   * Constant for cancel result String
   */
  public static final String CANCEL = "cancel";
  public static final String NOT_LOGGED_IN = "notLoggedIn";
  public static final String FAILURE = "failure";
  public static final String LOGGED_IN = "loggedIn";
  public static final String SOCIAL = "social";
  public static final String REDIRECT = "redirect";

  /**
   * Transient log to prevent session synchronization issues - children can
   * use instance for logging.
   */
  protected final transient Log log = LogFactory.getLog(getClass());

  /**
   * The UserManager
   */

  protected PersonManager personManager;

  /**
   * Indicator if the user clicked cancel
   */
  protected String cancel;

  /**
   * Indicator for the page the user came from.
   */
  protected String from;

  /**
   * Set to "delete" when a "delete" request parameter is passed in
   */
  protected String delete;

  /**
   * Set to "save" when a "save" request parameter is passed in
   */
  protected String save;

  /**
   * MailEngine for sending e-mail
   */
  protected MailEngine mailEngine;

  /**
   * A message pre-populated with default data
   */
  protected SimpleMailMessage mailMessage;

  /**
   * Velocity template to use for e-mailing
   */
  protected String templateName;

  /**
   * Simple method that returns "cancel" result
   *
   * @return "cancel"
   */
  public String cancel() {
    return CANCEL;
  }

  /**
   * Save the message in the session, appending if messages already exist
   *
   * @param msg the message to put in the session
   */
  @SuppressWarnings("unchecked")
  protected void saveMessage(String msg) {
    List messages = (List)getRequest().getSession().getAttribute("messages");
    if (messages == null) {
      messages = new ArrayList();
    }
    messages.add(msg);
    getRequest().getSession().setAttribute("messages", messages);
  }

  /**
   * Convenience method to get the Configuration HashMap from the servlet
   * context.
   *
   * @return the user's populated form from the session
   */
  protected Map getConfiguration() {
    Map config = (HashMap)getSession().getServletContext()
      .getAttribute(Constants.CONFIG);
    // so unit tests don't puke when nothing's been set
    if (config == null) {
      return new HashMap();
    }
    return config;
  }

  /**
   * Convenience method to get the request
   *
   * @return current request
   */
  protected HttpServletRequest getRequest() {
    return ServletActionContext.getRequest();
  }

  /**
   * Convenience method to get the response
   *
   * @return current response
   */
  protected HttpServletResponse getResponse() {
    return ServletActionContext.getResponse();
  }

  /**
   * Convenience method to get the session. This will create a session if one
   * doesn't exist.
   *
   * @return the session from the request (request.getSession()).
   */
  protected HttpSession getSession() {
    return getRequest().getSession();
  }


  /**
   * Convenience method to send e-mail to users
   *
   * @param person the user to send to
   * @param msg the message to send
   * @param url the URL to the application (or where ever you'd like to send
   * them)
   */
  protected void sendPersonMessage(Person person, String msg, String url, Map<String,Object> ctx) {
    if (log.isDebugEnabled()) {
      log.debug("sending e-mail to person [" + person.getEmail() + "]...");
    }

    mailMessage.setTo(person.getFullName() + "<" + person.getEmail() + ">");

    Map<String, Object> model = new HashMap<String, Object>();
    model.put("person", person);
    // TODO: figure out how to get bundle specified in struts.xml
    // model.put("bundle", getTexts());
    model.put("message", msg);
    model.put("applicationURL", url);

    for (Iterator iterator = ctx.entrySet().iterator(); iterator.hasNext();) {
      Map.Entry entry = (Map.Entry)iterator.next();
      model.put((String)entry.getKey(),(String)entry.getValue());
    }                        

    MailSender mailer = new MailSender(mailEngine,mailMessage,templateName,model);
    mailer.start();
  }

  public void setPersonManager(PersonManager personManager) {
    this.personManager = personManager;
  }

  public void setMailEngine(MailEngine mailEngine) {
    this.mailEngine = mailEngine;
  }

  public void setMailMessage(SimpleMailMessage mailMessage) {
    this.mailMessage = mailMessage;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  /**
   * Convenience method for setting a "from" parameter to indicate the
   * previous page.
   *
   * @param from indicator for the originating page
   */
  public void setFrom(String from) {
    this.from = from;
  }

  public void setDelete(String delete) {
    this.delete = delete;
  }

  public void setSave(String save) {
    this.save = save;
  }

  private class MailSender extends Thread {

    private MailEngine engine;
    private SimpleMailMessage message;
    private Map<String, Object> model;
    private String templateName;

    public MailSender(MailEngine engine, SimpleMailMessage message,
                      String templateName, Map<String, Object> model){
      this.engine = engine;
      this.message = message;
      this.templateName = templateName;
      this.model = model;
    }

    public MailEngine getEngine() {
      return engine;
    }

    public void setEngine(MailEngine engine) {
      this.engine = engine;
    }

    public SimpleMailMessage getMessage() {
      return message;
    }

    public void setMessage(SimpleMailMessage message) {
      this.message = message;
    }

    @Override
    public void run() {
      mailEngine.sendMessage(this.message,this.templateName,this.model);
    }
  }
}
