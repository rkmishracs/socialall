package com.mystatusonline.service;

import com.google.code.facebookapi.FacebookException;

import twitter4j.TwitterException;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 25, 2010 Time: 11:37:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatusUpdateException
  extends Exception{

  private Exception facebookException;
  private Exception twitterException;


  public StatusUpdateException(Exception facebookException, Exception twitterException) {
    this.facebookException = facebookException;
    this.twitterException = twitterException;
  }

  public Exception getFacebookException() {
    return facebookException;
  }

  public Exception getTwitterException() {
    return twitterException;
  }

  public void setFacebookException(FacebookException e) {
    facebookException = e;
  }

  public void setTwitterException(TwitterException e) {
    twitterException = e;
  }

  public boolean isException(){
    return facebookException != null || twitterException != null;
  }
}