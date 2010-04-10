package com.mystatusonline.webapp.action;


import org.apache.struts2.ServletActionContext;

import com.mystatusonline.service.PersonNotLoggedInException;
import com.mystatusonline.webapp.util.SessionUtil;


/**
 * Action to allow new users to sign up.
 */
public class FacebookAction
        extends BaseAction {

    private static final long serialVersionUID = 6558317334878272308L;

    /**
     * When method=GET, "input" is returned. Otherwise, "success" is returned.
     *
     * @return cancel, input or success
     */


    public String execute() {
        if (ServletActionContext.getRequest().getMethod().equals("GET")) {
            if (ServletActionContext.getRequest().getParameter("session") != null) {
                try {
                    personManager.facebookSave(SessionUtil.personLoggedInId(ServletActionContext.getRequest().getSession())
                            ,ServletActionContext.getRequest().getParameter("session"));
                } catch (PersonNotLoggedInException e) {
                    return NOT_LOGGED_IN;
                }
            } else if (ServletActionContext.getRequest().getParameter("fbCancelLogin") != null) {
                return FAILURE;
            }
            return SUCCESS;
        }
        saveMessage(getText("facebook.callbackerror"));
        return ERROR;
    }


    /*http://localhost:8080/updateStatus.jsp?fb_login&fname=_opener&guid=0.7248178422451019&session=
    {"session_key":"3.Q_ohruZw8Hn0EfuequyiMw__.3600.1266541200-100000769717398",
    "uid":100000769717398,"expires":1266541200,
    "secret":"lX1vA__QTthrJRVgIX6psg__",
    "base_domain":"localhost",
    "sig":"02e1cdcc24bedaee37b9141a6e449599"}
    &permissions=["publish_stream"]*/


    /**
     * Returns "input"
     *
     * @return "input" by default
     */
    public String doDefault() {
        return INPUT;
    }


}