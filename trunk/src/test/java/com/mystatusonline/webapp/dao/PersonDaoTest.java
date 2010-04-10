package com.mystatusonline.webapp.dao;

import java.util.List;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.mystatusonline.dao.PersonDao;
import com.mystatusonline.model.Person;

import junit.framework.Assert;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 2:11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonDaoTest
  extends BaseDaoTestCase {

  private PersonDao personDao = null;

  @Autowired
  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  // END SNIPPET: test
  // START SNIPPET: testFindPersonByLastName
  @Test
  public void testFindPersonByLastName()
    throws Exception {
    List<Person> people = personDao.findByLastName("Raible");
    Assert.assertTrue(people.size() > 0);
  }
  // END SNIPPET: testFindPersonByLastName

  // START SNIPPET: testAddAndRemovePerson

  @Test
  public void testAddAndRemovePerson()
    throws Exception {
    Person person = new Person();
    person.setFirstName("Country");
    person.setLastName("Bry");
    person.setUsername("username1");
    person.setPassword("password1");
    person.setEmail("email@email.com");

    person = personDao.save(person);
    flush();

    person = personDao.get(person.getId());

    Assert.assertEquals("Country", person.getFirstName());
    Assert.assertEquals("username1", person.getUsername());
    Assert.assertNotNull(person.getId());

    log.debug("removing person...");

    personDao.remove(person.getId());
    flush();

    try {
      personDao.get(person.getId());
      Assert.fail("Person found in database");
    } catch (DataAccessException dae) {
      log.debug("Expected exception: " + dae.getMessage());
      Assert.assertNotNull(dae);
    }
  }
  // END SNIPPET: testAddAndRemovePerson
}
