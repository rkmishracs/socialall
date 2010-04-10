package com.mystatusonline.webapp.action;


import org.apache.struts2.ServletActionContext;

import com.mystatusonline.webapp.util.SessionUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;


/**
 * Action to allow new users to sign up.
 */
public class TwitterAction
  extends BaseAction {

  private static final long serialVersionUID = 6558317334878272308L;
  private String twitterAppApiKey;
  private String twitterAppSecret;

  /**
   * When method=GET, "input" is returned. Otherwise, "success" is returned.
   *
   * @return cancel, input or success
   */


  public String execute() {
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {
      Twitter twitter = new TwitterFactory().getInstance();
      twitter.setOAuthConsumer(getTwitterAppApiKey(),
                               getTwitterAppSecret());
      try {
        RequestToken token = (RequestToken)ServletActionContext.getRequest().getSession().getAttribute("twitterRequestToken");
        AccessToken accessToken = twitter.getOAuthAccessToken(token);
        personManager.twitterSave(SessionUtil.personLoggedInId(ServletActionContext.getRequest().getSession()),
                                  String.valueOf(accessToken.getUserId()),accessToken.getToken(),accessToken.getTokenSecret());
      } catch (TwitterException e) {
        saveMessage(getText("twitter.callbackerror"));
        return ERROR;
      }
    }
    saveMessage(getText("twitter.successful"));
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
}