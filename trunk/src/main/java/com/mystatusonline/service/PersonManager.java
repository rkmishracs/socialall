package com.mystatusonline.service;

import com.google.code.facebookapi.FacebookException;
import com.mystatusonline.model.Person;
import com.mystatusonline.service.exception.MySpaceException;

import org.appfuse.service.GenericManager;
import twitter4j.TwitterException;

import java.util.List;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 4:23:30 PM
 * To change this template use File | Settings | File Templates.
 */

public interface PersonManager
  extends GenericManager<Person, Long> {

  List<Person> findByLastName(String lastName);
    
  boolean updateStatus(String phoneNumber, String status)
    throws StatusUpdateException;

  Person savePerson(Person person) throws PersonExistsException, UsernameExistsException, EmailExistsException, PhoneNumberExistsException;

  Person login(String username, String password);

  Person facebookSave(Long personId, String facebookQueryString);

  Person twitterSave(Long personId, String twitterUserId, String accessToken,
                     String accessTokenSecret);

  Person myspaceSave(Long personId, String myspaceId, String accessToken,
                     String accessTokenSecret);

  void twitterStatusUpdate(Long personId, String status)
    throws TwitterException;

  void myspaceStatusUpdate(Long personId, String status)
    throws MySpaceException;

  void facebookStatusUpdate(Long personId, String status)
    throws FacebookException;

  List<Person> findByEmail(String email) throws PersonNotExistsException;

  String resetPasswordToken(Long personId);

  void resetPassword(String token, Long personId, String password)
    throws InvalidPasswordResetTokenException;
}
