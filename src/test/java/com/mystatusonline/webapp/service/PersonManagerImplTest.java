package com.mystatusonline.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.easymock.EasyMock.*;

import com.mystatusonline.dao.PersonDao;
import com.mystatusonline.model.Person;
import com.mystatusonline.service.impl.PersonManagerImpl;

import junit.framework.TestCase;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 4:32:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonManagerImplTest
  extends TestCase {

  private final Log log = LogFactory.getLog(PersonManagerImplTest.class);
  private PersonManagerImpl manager = null;
  private PersonDao dao = null;
  private Person person = null;

  protected void setUp()
    throws Exception {
    log.debug("setUpDao for PersonManagerImplTest");
    dao = createStrictMock(PersonDao.class);
    manager = new PersonManagerImpl((PersonDao)dao);
  }

  public void testGetPerson() {
    log.debug("testing getPerson");

    Long id = 7L;
    person = new Person();

    // set expected behavior on dao
    expect(dao.get(id)).andReturn(person);
    replay(dao);

    Person result = manager.get(id);
    assertSame(person, result);
    verify(dao);
  }

  public void testGetPersons() {
    log.debug("testing getPersons");

    List people = new ArrayList();

    // set expected behavior on dao
    expect(dao.getAll()).andReturn(people);
    replay(dao);

    List result = manager.getAll();
    assertSame(people, result);
    verify(dao);
  }

  public void testGetByLastName() {
    log.debug("testing getByLastName");

    List people = new ArrayList();
    String lastName = "Smith";

    // set expected behavior on dao
    expect(dao.findByLastName(lastName)).andReturn(people);
    replay(dao);

    List result = manager.findByLastName(lastName);
    assertSame(people, result);
    verify(dao);
  }

  public void testSavePerson() {
    log.debug("testing savePerson");

    person = new Person();

    // set expected behavior on dao
    expect(dao.save(person)).andReturn(person);
    replay(dao);

    manager.save(person);
    verify(dao);
  }

  public void testRemovePerson() {
    log.debug("testing removePerson");

    Long id = 11L;
    person = new Person();

    // set expected behavior on dao
    dao.remove(id);
    replay(dao);

    manager.remove(id);
    verify(dao);
  }
}
