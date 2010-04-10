package com.mystatusonline.webapp.action;

import java.util.List;

import com.mystatusonline.service.PersonNotLoggedInException;
import org.apache.struts2.ServletActionContext;

import com.mystatusonline.model.Person;
import com.mystatusonline.webapp.util.SessionUtil;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 5:22:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonAction
  extends BaseAction {

  private List persons;
  private Person person;

  public List getPersons() {
    return persons;
  }

  public String list() {
    persons = personManager.getAll();
    return SUCCESS;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String execute() {
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {
      if (SessionUtil
        .isPersonLoggedIn(ServletActionContext.getRequest().getSession())) {
          try {
              person = personManager.get(SessionUtil.personLoggedInId(
                ServletActionContext.getRequest().getSession()));
          } catch (PersonNotLoggedInException e) {
              return NOT_LOGGED_IN;
          }
      }
      return INPUT;
    }
    return SUCCESS;
  }

  public String delete() {
    personManager.remove(person.getId());
    saveMessage(getText("person.deleted"));
    return SUCCESS;
  }

  public String savePerson()
    throws Exception {
    if (cancel != null) {
      return CANCEL;
    }

    if (delete != null) {
      return delete();
    }

    boolean isNew = (person.getId() == null);

    person = personManager.savePerson(person);

    String key = (isNew) ? "person.added" : "person.updated";
    saveMessage(getText(key));
    return SUCCESS;
    
  }

}