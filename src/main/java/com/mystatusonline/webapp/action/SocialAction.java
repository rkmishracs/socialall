package com.mystatusonline.webapp.action;


import java.io.*;
import java.net.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.opensocial.auth.OAuth3LeggedScheme;
import org.opensocial.providers.MySpaceProvider;

import net.oauth.OAuthException;

import com.mystatusonline.model.Person;
import com.mystatusonline.model.SocialNetwork;
import com.mystatusonline.service.PersonNotLoggedInException;
import com.mystatusonline.webapp.util.RequestUtil;
import com.mystatusonline.webapp.util.SessionUtil;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.RequestToken;


/**
 * Action to allow new users to sign up.
 */
public class SocialAction
  extends BaseAction {


  private static final long serialVersionUID = 6558317334878272308L;

  private static final String SCHEME_KEY = "scheme";
  private String cancel;
  private String status;
  private String url;
  private SocialNetwork facebook;
  private SocialNetwork twitter;
  private SocialNetwork myspace;
  private String twitterAppApiKey;
  private String twitterAppSecret;
  private String myspaceAppApiKey;
  private String myspaceAppSecret;
  private String facebookAppApiKey;

  public SocialNetwork getFacebook() {
    return facebook;
  }

  public void setFacebook(SocialNetwork facebook) {
    this.facebook = facebook;
  }

  public SocialNetwork getTwitter() {
    return twitter;
  }

  public void setTwitter(SocialNetwork twitter) {
    this.twitter = twitter;
  }

  public SocialNetwork getMyspace() {
    return myspace;
  }

  public void setMyspace(SocialNetwork myspace) {
    this.myspace = myspace;
  }

  public void setCancel(String cancel) {
    this.cancel = cancel;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * When method=GET, "input" is returned. Otherwise, "success" is returned.
   *
   * @return cancel, input or success
   */
  public String execute() {
    ServletActionContext.getRequest().getSession()
      .setAttribute("facebookAppApiKey", facebookAppApiKey);
    if (cancel != null) {
      return CANCEL;
    }
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {
      Person person = null;
      try {
        person = personManager.get(SessionUtil.personLoggedInId(
          ServletActionContext.getRequest().getSession()));
        facebook = person.getNetwork(SocialNetwork.FACEBOOK);
        twitter = person.getNetwork(SocialNetwork.TWITTER);
        myspace = person.getNetwork(SocialNetwork.MYSPACE);
      } catch (PersonNotLoggedInException e) {
        return NOT_LOGGED_IN;
      }
      //todo: THIS NEEDS TO SHOW TWITTER MESSAGE
      //twitter = person.getNetwork(SocialNetwork.TWITTER);
      if (ServletActionContext.getRequest().getParameter("message") != null) {
        saveMessage(getText("facebook.forceLogin"));
      }
      return INPUT;
    }
    return SUCCESS;
  }

  /**
   * Returns "input"
   *
   * @return "input" by default
   */
  public String doDefault() {
    return INPUT;
  }

  public String twitterSave() {
    try {
      Twitter twitter = new TwitterFactory().getInstance();
      twitter.setOAuthConsumer(getTwitterAppApiKey(), getTwitterAppSecret());
      RequestToken requestToken = twitter.getOAuthRequestToken();
      ServletActionContext.getRequest().getSession()
        .setAttribute("twitterRequestToken", requestToken);
      url = requestToken.getAuthorizationURL();
      return REDIRECT;
    } catch (Exception e) {
      e.printStackTrace();
      return ERROR;
    }

  }

  public String myspaceSave() {
    /*HttpServletRequest request = ServletActionContext.getRequest();
   request.getSession().setAttribute("accessTokenKey", null);
   request.getSession().setAttribute("accessTokenSecret", null);

   OffsiteContext c1 = new OffsiteContext(getMyspaceAppApiKey(),
           getMyspaceAppSecret());
   OAuthToken requestToken = c1
           .getRequestToken(RequestUtil.getAppURL(request)+"/myspace.html?callback=true");
   request.getSession()
           .setAttribute("requestTokenSecret", requestToken.getSecret());
   System.out.println(requestToken);
   url = c1.getAuthorizationURL(requestToken);
   return REDIRECT;*/

    HttpServletRequest request = ServletActionContext.getRequest();
    OAuth3LeggedScheme scheme = new OAuth3LeggedScheme(new MySpaceProvider(),
                                                       getMyspaceAppApiKey(),
                                                       getMyspaceAppSecret());
    try {
      url = scheme.getAuthorizationUrl(
        RequestUtil.getAppURL(request) + "/myspace.html?callback=true");
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return ERROR;
    } catch (IOException e) {
      e.printStackTrace();
      return ERROR;
    } catch (OAuthException e) {
      e.printStackTrace();
      return ERROR;
    }
    request.getSession().setAttribute(SCHEME_KEY, scheme);
    return REDIRECT;
  }

  public void setTwitterAppApiKey(String twitterAppApiKey) {
    this.twitterAppApiKey = twitterAppApiKey;
  }

  public String getTwitterAppApiKey() {
    return twitterAppApiKey;
  }

  public void setTwitterAppSecret(String twitterAppSecret) {
    this.twitterAppSecret = twitterAppSecret;
  }

  public String getTwitterAppSecret() {
    return twitterAppSecret;
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

  public void setFacebookAppApiKey(String facebookAppApiKey) {
    this.facebookAppApiKey = facebookAppApiKey;
  }

  public String getFacebookAppApiKey() {
    return facebookAppApiKey;
  }
}