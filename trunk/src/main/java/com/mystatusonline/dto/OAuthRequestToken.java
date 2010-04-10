package com.mystatusonline.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pritesh
 * Date: Mar 7, 2010
 * Time: 3:18:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class OAuthRequestToken implements Serializable{
    private String token;
    private String tokenSecret;
    private String url;

    public OAuthRequestToken(String token, String tokenSecret, String url) {
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
