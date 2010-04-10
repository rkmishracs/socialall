package com.mystatusonline.service;

import com.google.code.facebookapi.FacebookException;
import com.mystatusonline.model.Person;
import com.mystatusonline.service.exception.MySpaceException;

import twitter4j.TwitterException;

import javax.jws.WebService;
import java.util.List;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 4:23:30 PM
 * To change this template use File | Settings | File Templates.
 */

@WebService
public interface PersonService {

  List<Person> findByLastName(String lastName);

  Person savePerson(Person person) throws PersonExistsException, UsernameExistsException, EmailExistsException, PhoneNumberExistsException;

  boolean updateStatus(String phoneNumber, String status)
    throws StatusUpdateException;

  Person login(String username, String password);

  Person facebookSave(Long personId, String facebookQueryString);

  Person twitterSave(Long personId, String twitterUserId, String accessToken,
                     String accessTokenSecret);
  
  Person myspaceSave(Long personId, String myspaceUid, String accessToken, String accessTokenSecret);

  void myspaceStatusUpdate(Long personId, String status)
    throws MySpaceException;

  void twitterStatusUpdate(Long personId, String status)
    throws TwitterException;

  void facebookStatusUpdate(Long personId, String status)
    throws FacebookException;

  List<Person> findByEmail(String email) throws PersonNotExistsException;

  String resetPasswordToken(Long personId);

  void resetPassword(String token, Long personId, String password)
    throws InvalidPasswordResetTokenException;
}