package com.mystatusonline.webapp.service;

import com.mystatusonline.service.*;
import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mystatusonline.model.Person;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 11, 2010 Time: 6:04:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonServiceTest extends BaseDaoTestCase {

  private PersonManager personManager;

  @Autowired
  public void setPersonManager(PersonManager personManager) {
    this.personManager = personManager;
  }

  @Test
  public void testPerson()
          throws PersonExistsException, EmailExistsException, UsernameExistsException, PhoneNumberExistsException {
    Person person = new Person("username","password","password","pdamani@atypon.com");
    personManager.savePerson(person);
  }
}
