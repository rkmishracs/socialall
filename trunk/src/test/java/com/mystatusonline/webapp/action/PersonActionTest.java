package com.mystatusonline.webapp.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;

import com.mystatusonline.model.Person;
import com.mystatusonline.service.PersonManager;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 5:21:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonActionTest extends BaseActionTestCase {
    private PersonAction action;

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        action = new PersonAction();
        PersonManager personManager = (PersonManager)applicationContext.getBean("personManager");
        action.setPersonManager(personManager);

        // add a test person to the database
        Person person = new Person();
        person.setFirstName("Jack");
        person.setLastName("Raible");
        personManager.save(person);
    }

    public void testSearch() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getPersons().size() >= 1);
    }

    public void testEdit() throws Exception {
      log.debug("testing edit...");
      assertNull(action.getPerson());
      assertEquals("success", action.savePerson());
      assertNotNull(action.getPerson());
      assertFalse(action.hasActionErrors());
  }

  public void testSave() throws Exception {
      MockHttpServletRequest request = new MockHttpServletRequest();
      ServletActionContext.setRequest(request);
      assertEquals("success", action.savePerson());
      assertNotNull(action.getPerson());

      // update last name and save
      action.getPerson().setLastName("Updated Last Name");
      assertEquals("input", action.savePerson());
      assertEquals("Updated Last Name", action.getPerson().getLastName());
      assertFalse(action.hasActionErrors());
      assertFalse(action.hasFieldErrors());
      assertNotNull(request.getSession().getAttribute("messages"));
  }

  public void testRemove() throws Exception {
      MockHttpServletRequest request = new MockHttpServletRequest();
      ServletActionContext.setRequest(request);
      action.setDelete("");
      Person person = new Person();
      person.setId(2L);
      action.setPerson(person);
      assertEquals("success", action.delete());
      assertNotNull(request.getSession().getAttribute("messages"));
  }
}
