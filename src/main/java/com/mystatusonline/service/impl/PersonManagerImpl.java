package com.mystatusonline.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.appfuse.service.impl.GenericManagerImpl;
import org.opensocial.Client;
import org.opensocial.Request;
import org.opensocial.auth.OAuth3LeggedScheme;
import org.opensocial.models.Activity;
import org.opensocial.models.myspace.StatusMood;
import org.opensocial.providers.MySpaceProvider;
import org.opensocial.services.ActivitiesService;
import org.opensocial.services.myspace.StatusMoodsService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.mystatusonline.dao.PersonDao;
import com.mystatusonline.model.Person;
import com.mystatusonline.model.SocialNetwork;
import com.mystatusonline.service.EmailExistsException;
import com.mystatusonline.service.InvalidPasswordResetTokenException;
import com.mystatusonline.service.PersonExistsException;
import com.mystatusonline.service.PersonManager;
import com.mystatusonline.service.PersonNotExistsException;
import com.mystatusonline.service.PersonService;
import com.mystatusonline.service.PhoneNumberExistsException;
import com.mystatusonline.service.StatusUpdateException;
import com.mystatusonline.service.UsernameExistsException;
import com.mystatusonline.service.exception.MySpaceException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 4:24:00 PM
 * To change this template use File | Settings | File Templates.
 */

@Service("personManager")
@WebService(serviceName = "PersonService",
            endpointInterface = "com.mystatusonline.service.PersonService")

public class PersonManagerImpl
  extends GenericManagerImpl<Person, Long>
  implements PersonManager, PersonService {

  PersonDao personDao;
  private PasswordEncoder passwordEncoder;

  private String facebookAppApiKey;
  private String facebookAppSecret;
  private String twitterAppApiKey;
  private String twitterAppSecret;
  private String myspaceAppApiKey;
  private String myspaceAppSecret;

  public PersonManagerImpl() {
  }

  public String getFacebookAppApiKey() {
    return facebookAppApiKey;
  }

  public void setFacebookAppApiKey(String facebookAppApiKey) {
    this.facebookAppApiKey = facebookAppApiKey;
  }

  public String getFacebookAppSecret() {
    return facebookAppSecret;
  }

  public void setFacebookAppSecret(String facebookAppSecret) {
    this.facebookAppSecret = facebookAppSecret;
  }

  public String getTwitterAppApiKey() {
    return twitterAppApiKey;
  }

  public void setTwitterAppApiKey(String twitterAppApiKey) {
    this.twitterAppApiKey = twitterAppApiKey;
  }

  public String getTwitterAppSecret() {
    return twitterAppSecret;
  }

  public void setTwitterAppSecret(String twitterAppSecret) {
    this.twitterAppSecret = twitterAppSecret;
  }

  public String getMyspaceAppApiKey() {
    return myspaceAppApiKey;
  }

  public void setMyspaceAppApiKey(String myspaceAppApiKey) {
    this.myspaceAppApiKey = myspaceAppApiKey;
  }

  public String getMyspaceAppSecret() {
    return myspaceAppSecret;
  }

  public void setMyspaceAppSecret(String myspaceAppSecret) {
    this.myspaceAppSecret = myspaceAppSecret;
  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }


  public PersonManagerImpl(PersonDao personDao) {
    super(personDao);
    this.personDao = personDao;
  }

  public List<Person> findByLastName(String lastName) {
    return personDao.findByLastName(lastName);
  }

  public List<Person> findByEmail(String email)
    throws PersonNotExistsException {
    List<Person> persons = personDao.findByEmail(email);
    if (persons == null || persons.size() == 0) {
      throw new PersonNotExistsException(email);
    }
    return persons;
  }

  public String resetPasswordToken(Long personId) {
    Person person = personDao.get(personId);
    return passwordEncoder.encodePassword(person.getEmail() + personId, null);
  }

  public void resetPassword(String token, Long personId, String password)
    throws InvalidPasswordResetTokenException {
    Person person = personDao.get(personId);
    if (passwordEncoder.encodePassword(person.getEmail() + personId, null)
      .equals(token)) {
      person.setPassword(passwordEncoder.encodePassword(password, null));
      personDao.save(person);
    } else {
      log
        .debug("token " + token + " for person id " + personId + " is invalid");
      throw new InvalidPasswordResetTokenException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Person savePerson(Person person)
    throws PersonExistsException, UsernameExistsException, EmailExistsException,
           PhoneNumberExistsException {
    Person personToUpdate = null;
    if (person.getVersion() == null) {
      //new
      List<Person> persons = personDao.findByEmail(person.getEmail());
      if (persons != null && persons.size() > 0) {
        throw new EmailExistsException("Email Already Used");
      }
      persons = personDao.findByUserName(person.getUsername());
      if (persons != null && persons.size() > 0) {
        throw new UsernameExistsException("Username Already Used");
      }
      persons = personDao.findByPhoneNumber(person.getPhoneNumber());
      if (persons != null && persons.size() > 0) {
        throw new PhoneNumberExistsException("Phone Already Used");
      }
      person.setUsername(person.getUsername().toLowerCase());
      setPassword(person, "");
      personToUpdate = person;
    } else {
      //update
      Person personInDb = personDao.get(person.getId());
      setPassword(person, personInDb.getPassword());
      copyPersonUpdates(person, personInDb);
      personToUpdate = personInDb;
    }

    try {
      return personDao.save(personToUpdate);
    } catch (DataIntegrityViolationException e) {
      //e.printStackTrace();
      log.error(e);
      throw new PersonExistsException(
        "User '" + person.getUsername() + "' already exists!");
    } catch (JpaSystemException e) { // needed for JPA
      //e.printStackTrace();
      log.error(e);
      throw new PersonExistsException(
        "User '" + person.getUsername() + "' already exists!");
    }
  }

  public boolean updateStatus(String phoneNumber, String status)
    throws StatusUpdateException {
    StatusUpdateException exception = new StatusUpdateException(null, null);
    List<Person> persons = personDao.findByPhoneNumber(phoneNumber);
    if (persons != null && persons.size() > 0) {
      Person person = persons.get(0);
      if (person.isFBInit()) {
        try {
          facebookStatusUpdate(person.getId(), status);
        } catch (FacebookException e) {
          log.error(e);
          exception.setFacebookException(e);
        }
      }
      if (person.isTwitterInit()) {
        try {
          twitterStatusUpdate(person.getId(), status);
        } catch (TwitterException e) {
          log.error(e);
          exception.setTwitterException(e);
        }
      }
    }
    if (exception.isException()) {
      throw exception;
    } else {
      return true;
    }
  }

  private void copyPersonUpdates(Person from, Person to) {
    to.setFirstName(from.getFirstName());
    to.setLastName(from.getLastName());
    to.setEmail(from.getEmail());
    to.setPassword(from.getPassword());
    to.setUsername(from.getUsername());
    to.setPhoneNumber(from.getPhoneNumber());
  }

  private void setPassword(Person person, String currentPassword) {
    boolean passwordChanged = false;
    if (person.getVersion() == null) {
      passwordChanged = true;
    }
    if (passwordEncoder != null) {
      // Check whether we have to encrypt (or re-encrypt) the password
      if (person.getVersion() == null) {
        // New person, always encrypt
        passwordChanged = true;
      } else {
        // Existing person, check password in DB
        if (currentPassword == null) {
          passwordChanged = true;
        } else {
          if (!currentPassword.equals(
            passwordEncoder.encodePassword(person.getPassword(), null)) &&
              !currentPassword.equals(person.getPassword())) {
            passwordChanged = true;
          }
        }
      }

      // If password was changed (or new person), encrypt it
      if (passwordChanged) {
        person.setPassword(
          passwordEncoder.encodePassword(person.getPassword(), null));
      }
    } else {
      log.warn("PasswordEncoder not set, skipping password encryption...");
    }
  }

  public Person login(String username, String password) {
    String pass = personDao.getUserPassword(username);
    boolean loggedIn = false;
    if (passwordEncoder != null) {
      loggedIn = passwordEncoder.encodePassword(password, null).equals(pass);
    } else {
      loggedIn = password.equals(pass);
    }

    if (loggedIn) {
      List<Person> persons = personDao.findByUserName(username);
      return persons.get(0);
    }
    return null;
  }

  public Person facebookSave(Long personId, String facebookSession) {
    Person person = personDao.get(personId);
    SocialNetwork facebook = person.getNetwork(SocialNetwork.FACEBOOK);
    if (facebook == null) {
      facebook = new SocialNetwork(SocialNetwork.FACEBOOK);
      person.addSocialNetwork(facebook);
    }

    int from = facebookSession.indexOf("{");
    int to = facebookSession.indexOf("}");

    String sessionStr = facebookSession.substring(from + 1, to);
    String[] params = sessionStr.split(",");
    for (int i = 0; i < params.length; i++) {
      String param = params[i];
      String[] subParams = param.split(":");
      if (subParams[0].toLowerCase().contains("session")) {
        facebook.setFacebookSessionKey(subParams[1].replace("\"", ""));
      } else if (subParams[0].toLowerCase().contains("uid")) {
        facebook.setFacebookUid(Long.parseLong(subParams[1].replace("\"", "")));
      } else if (subParams[0].toLowerCase().contains("expires")) {
        facebook
          .setFacebookExpires(Long.parseLong(subParams[1].replace("\"", "")));
      } else if (subParams[0].toLowerCase().contains("secret")) {
        facebook.setFacebookSecret(subParams[1].replace("\"", ""));
      } else if (subParams[0].toLowerCase().contains("sig")) {
        facebook.setFacebookSig(subParams[1].replace("\"", ""));
      }
    }
    return personDao.save(person);
  }

  public Person twitterSave(Long personId, String twitterUserId,
                            String accessToken, String accessTokenSecret) {
    Person person = personDao.get(personId);
    SocialNetwork twitter = person.getNetwork(SocialNetwork.TWITTER);
    if (twitter == null) {
      twitter = new SocialNetwork(SocialNetwork.TWITTER);
      person.addSocialNetwork(twitter);
    }
    twitter.setOAuthAccessToken(accessToken);
    twitter.setOAuthAccessTokenSecret(accessTokenSecret);
    twitter.setSocialUid(twitterUserId);
    return personDao.save(person);
  }

  public Person myspaceSave(Long personId, String myspaceUid,
                            String accessToken, String accessTokenSecret) {
    Person person = personDao.get(personId);
    SocialNetwork myspace = person.getNetwork(SocialNetwork.MYSPACE);
    if (myspace == null) {
      myspace = new SocialNetwork(SocialNetwork.MYSPACE);
      person.addSocialNetwork(myspace);
    }
    myspace.setOAuthAccessToken(accessToken);
    myspace.setOAuthAccessTokenSecret(accessTokenSecret);
    myspace.setSocialUid(myspaceUid);
    return personDao.save(person);
  }

  public void twitterStatusUpdate(Long personId, String status)
    throws TwitterException {
    try {
      Person person = personDao.get(personId);
      SocialNetwork network = person.getNetwork(SocialNetwork.TWITTER);
      // twitter

      Twitter twitter = new TwitterFactory().getInstance();
      twitter.setOAuthConsumer(twitterAppApiKey, twitterAppSecret);
      twitter.setOAuthAccessToken(new AccessToken(network.getOAuthAccessToken(),
                                                  network.getOAuthAccessTokenSecret()));
      twitter.updateStatus(status);
    } catch (TwitterException e) {
      log.error("Could not update twitter status for personId" + personId, e);
      throw e;
    }
  }

  public void myspaceStatusUpdate(Long personId, String status)
    throws MySpaceException {
    Person person = personDao.get(personId);
    SocialNetwork network = person.getNetwork(SocialNetwork.MYSPACE);
    /*OffsiteContext c = new OffsiteContext(getMyspaceAppApiKey(), getMyspaceAppSecret());
 c.setAccessToken(new OAuthToken(network.getOAuthAccessToken(), network.getOAuthAccessTokenSecret()));
 RestV1 r = new RestV1(c);
 Object obj = r.setStatus(network.getSocialUid(),status);*/

    OAuth3LeggedScheme scheme = new OAuth3LeggedScheme(new MySpaceProvider(),
                                                       getMyspaceAppApiKey(),
                                                       getMyspaceAppSecret());
    scheme.setAccessToken(
      new OAuth3LeggedScheme.Token(network.getOAuthAccessToken(),
                                   network.getOAuthAccessTokenSecret()));
    Client client = new Client(scheme.getProvider(), scheme);
    try {
      StatusMood statusMood = new StatusMood();
      statusMood.setStatus(status);
      Request request = StatusMoodsService.updateStatus(statusMood);
      client.send(request);
    } catch (Exception e) {
      throw new MySpaceException(e);
    }
  }

  public void facebookStatusUpdate(Long personId, String status)
    throws FacebookException {
    try {
      Person person = personDao.get(personId);
      sendToFB(person, status);
    } catch (FacebookException e) {
      log.error("Could not update facebook status for personId" + personId, e);
      throw e;
    }
  }

  public void sendToFB(Person person, String message)
    throws FacebookException {

    FacebookJsonRestClient facebook = new FacebookJsonRestClient(
      facebookAppApiKey,
      facebookAppSecret,
      person.getNetwork(SocialNetwork.FACEBOOK).getFacebookSessionKey());


    try {/*
            String query = "SELECT name FROM user WHERE uid=" + user;
            org.json.JSONArray resultArray = (org.json.JSONArray) facebook.fql_query(query); */
      facebook.users_setStatus(message);
    }
    catch (FacebookException ex) {
      throw ex;
    }

    /*String FB_APP_API_KEY = new String("c91669907e4f7c8d2024fff73f3af917");
    String FB_APP_SECRET = new String("9688fc7213905f352c63644384f08679");
    //String FB_SESSION_KEY = new String("XXXXXXXXXXXXX");
    FacebookJsonRestClient facebook = new FacebookJsonRestClient( FB_APP_API_KEY, FB_APP_SECRET);
    FacebookJsonRestClient facebookClient = (FacebookJsonRestClient)facebook;


    *//**//*Attachment attachment = new Attachment();
    attachment.setCaption("Caption for attachment");
    attachment.setDescription("Description for attachment");
    attachment.setHref("http://XXXXXXXXXX.com");
    attachment.setName("Hopefully this works");


    AttachmentMediaImage attach_media = new AttachmentMediaImage(FB_SESSION_KEY, FB_SESSION_KEY);

    attach_media.setHref("http://XXXXXXX.com");
    attach_media.setSrc("http://XXXXXXXXX.jpg");
    attachment.setMedia(attach_media);*//**//*
    facebookClient.stream_publish(message, null, null, null, null);
        System.out.println("successfully updated");*/
  }
}