package com.mystatusonline.webapp.action;


import org.apache.struts2.ServletActionContext;

import com.google.code.facebookapi.FacebookException;
import com.mystatusonline.model.Person;
import com.mystatusonline.model.SocialNetwork;
import com.mystatusonline.service.PersonNotLoggedInException;
import com.mystatusonline.service.exception.MySpaceException;
import com.mystatusonline.webapp.util.SessionUtil;

import twitter4j.TwitterException;


/**
 * Action to allow new users to sign up.
 */
public class NetworkAction
        extends BaseAction {

    private static final long serialVersionUID = 6558317334878272308L;

    private String cancel;
    private String status;
    private String network;
    private SocialNetwork twitter;
    private SocialNetwork facebook;
    private SocialNetwork myspace;

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public SocialNetwork getTwitter() {
        return twitter;
    }

    public void setTwitter(SocialNetwork twitter) {
        this.twitter = twitter;
    }

    public SocialNetwork getFacebook() {
        return facebook;
    }

    public void setFacebook(SocialNetwork facebook) {
        this.facebook = facebook;
    }

    public SocialNetwork getMyspace() {
        return myspace;
    }

    public void setMyspace(SocialNetwork myspace) {
        this.myspace = myspace;
    }

    /**
     * When method=GET, "input" is returned. Otherwise, "success" is returned.
     *
     * @return cancel, input or success
     */
    public String execute() {
        if (cancel != null) {
            return CANCEL;
        }
        if (ServletActionContext.getRequest().getMethod().equals("GET")) {
            if (SessionUtil
                    .isPersonLoggedIn(ServletActionContext.getRequest().getSession())) {
                try {
                    Person person = personManager.get(SessionUtil.personLoggedInId(
                            ServletActionContext.getRequest().getSession()));
                    if (person.isTwitterInit()){
                        twitter = person.getNetwork(SocialNetwork.TWITTER);
                    }
                    if (person.isFBInit()){
                        facebook = person.getNetwork(SocialNetwork.FACEBOOK);
                    }
                    if (person.isMySpaceInit()){
                        myspace = person.getNetwork(SocialNetwork.MYSPACE);
                    }
                } catch (PersonNotLoggedInException e) {
                    return NOT_LOGGED_IN;
                }
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


    public String updateStatus() {
        try {

            Long personId = SessionUtil
                    .personLoggedInId(ServletActionContext.getRequest().getSession());
            if (network.equalsIgnoreCase("twitter")) {
                personManager.twitterStatusUpdate(personId, status);
            } else if (network.equalsIgnoreCase("facebook")) {
                personManager.facebookStatusUpdate(personId, status);
            } else if (network.equalsIgnoreCase("myspace")) {
                personManager.myspaceStatusUpdate(personId, status);
            }
            saveMessage(getText("updateStatus.success"));
            return SUCCESS;
        } catch (TwitterException e) {
            addActionError(getText("twitter.error"));
            return ERROR;
        } catch (FacebookException e) {
            addActionError(getText("facebook.error"));
            return ERROR;
        } catch (MySpaceException e) {
            addActionError(getText("myspace.error"));
            return ERROR;
        }
    }

}