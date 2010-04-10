package com.mystatusonline.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mystatusonline.model.Person;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 2:07:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PersonDao
  extends GenericDao<Person, Long> {

  List<Person> findByLastName(String lastName);

  List<Person> findByPhoneNumber(String phoneNumber);

  List<Person> findByUserName(String username);

  List<Person> findByEmail(String email);

  String getUserPassword(String username);
}
