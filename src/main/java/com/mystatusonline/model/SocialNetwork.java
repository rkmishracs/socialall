package com.mystatusonline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.appfuse.model.BaseObject;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 12:21:30 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "SocialNetwork")
public class SocialNetwork
  extends BaseObject {

  public final static int FACEBOOK = 1;
  public final static int TWITTER = 2;
  public final static int GOOGLE_BUZZ = 3;
  public final static int MYSPACE = 4;

  /*

    public enum SocialNetworkType {
      FACEBOOK(1),
      TWITTER(2);

      private final int type;
      SocialNetworkType(int type) {
          this.type = type;
      }
    }
  */

  private Long id;
  private int socialNetworkType;// required

  private String facebookSessionKey;
  private Long facebookUid;
  private Long facebookExpires;
  private String facebookSecret;
  private String facebookSig;

  private String socialUid;
  private String OAuthAccessToken;
  private String OAuthAccessTokenSecret;


  protected SocialNetwork() {
  }

  public SocialNetwork(int socialNetworkType) {
    this.socialNetworkType = socialNetworkType;
  }


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "socialNetworkType")
  public int getSocialNetworkType() {
    return socialNetworkType;
  }

  public void setSocialNetworkType(int socialNetworkType) {
    this.socialNetworkType = socialNetworkType;
  }

  @Column(name = "facebookSessionKey")
  public String getFacebookSessionKey() {
    return facebookSessionKey;
  }

  public void setFacebookSessionKey(String facebookSessionKey) {
    this.facebookSessionKey = facebookSessionKey;
  }

  @Column(name = "facebookUid")
  public Long getFacebookUid() {
    return facebookUid;
  }

  public void setFacebookUid(Long facebookUid) {
    this.facebookUid = facebookUid;
  }

  @Column(name = "facebookSig")
  public String getFacebookSig() {
    return facebookSig;
  }

  public void setFacebookSig(String facebookSig) {
    this.facebookSig = facebookSig;
  }

  @Column(name = "facebookExpires")
  public Long getFacebookExpires() {
    return facebookExpires;
  }

  public void setFacebookExpires(Long facebookExpires) {
    this.facebookExpires = facebookExpires;
  }

  @Column(name = "facebookSecret")
  public String getFacebookSecret() {
    return facebookSecret;
  }

  public void setFacebookSecret(String facebookSecret) {
    this.facebookSecret = facebookSecret;
  }

  @Column(name = "socialUid")
  public String getSocialUid() {
    return socialUid;
  }

  public void setSocialUid(String socialUid) {
    this.socialUid = socialUid;
  }

  @Column(name = "oauthAccessToken")
  public String getOAuthAccessToken() {
    return OAuthAccessToken;
  }

  public void setOAuthAccessToken(String OAuthAccessToken) {
    this.OAuthAccessToken = OAuthAccessToken;
  }

  @Column(name = "oauthAccessTokenSecret")
  public String getOAuthAccessTokenSecret() {
    return OAuthAccessTokenSecret;
  }

  public void setOAuthAccessTokenSecret(String OAuthAccessTokenSecret) {
    this.OAuthAccessTokenSecret = OAuthAccessTokenSecret;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + socialNetworkType;
    result = 31 * result +
             (facebookSessionKey != null ? facebookSessionKey.hashCode() : 0);
    result = 31 * result + (facebookUid != null ? facebookUid.hashCode() : 0);
    result =
      31 * result + (facebookExpires != null ? facebookExpires.hashCode() : 0);
    result =
      31 * result + (facebookSecret != null ? facebookSecret.hashCode() : 0);
    result = 31 * result + (facebookSig != null ? facebookSig.hashCode() : 0);
    result = 31 * result + (socialUid != null ? socialUid.hashCode() : 0);
    result =
      31 * result +
      (OAuthAccessToken != null ? OAuthAccessToken.hashCode() : 0);
    result = 31 * result +
             (OAuthAccessTokenSecret != null ?
              OAuthAccessTokenSecret.hashCode() :
              0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SocialNetwork that = (SocialNetwork)o;

    if (socialNetworkType != that.socialNetworkType) {
      return false;
    }
    if (OAuthAccessToken != null ?
        !OAuthAccessToken.equals(that.OAuthAccessToken) :
        that.OAuthAccessToken != null) {
      return false;
    }
    if (OAuthAccessTokenSecret != null ?
        !OAuthAccessTokenSecret.equals(that.OAuthAccessTokenSecret) :
        that.OAuthAccessTokenSecret != null) {
      return false;
    }
    if (socialUid != null ? !socialUid.equals(that.socialUid) :
        that.socialUid != null) {
      return false;
    }
    if (facebookExpires != null ?
        !facebookExpires.equals(that.facebookExpires) :
        that.facebookExpires != null) {
      return false;
    }
    if (facebookSecret != null ? !facebookSecret.equals(that.facebookSecret) :
        that.facebookSecret != null) {
      return false;
    }
    if (facebookSessionKey != null ?
        !facebookSessionKey.equals(that.facebookSessionKey) :
        that.facebookSessionKey != null) {
      return false;
    }
    if (facebookSig != null ? !facebookSig.equals(that.facebookSig) :
        that.facebookSig != null) {
      return false;
    }
    if (facebookUid != null ? !facebookUid.equals(that.facebookUid) :
        that.facebookUid != null) {
      return false;
    }
    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return "SocialNetwork{" +
           "id=" + id +
           ", socialNetworkType=" + socialNetworkType +
           ", facebookSessionKey='" + facebookSessionKey + '\'' +
           ", facebookUid=" + facebookUid +
           ", facebookExpires=" + facebookExpires +
           ", facebookSecret='" + facebookSecret + '\'' +
           ", facebookSig='" + facebookSig + '\'' +
           ", socialUid='" + socialUid + '\'' +
           ", OAuthAccessToken='" + OAuthAccessToken + '\'' +
           ", OAuthAccessTokenSecret='" + OAuthAccessTokenSecret + '\'' +
           '}';
  }
}