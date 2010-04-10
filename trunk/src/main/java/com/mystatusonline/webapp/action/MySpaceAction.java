package com.mystatusonline.webapp.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.opensocial.Client;
import org.opensocial.Request;
import org.opensocial.Response;
import org.opensocial.auth.OAuth3LeggedScheme;
import org.opensocial.models.Person;
import org.opensocial.services.PeopleService;

import com.mystatusonline.webapp.util.SessionUtil;



/**
 * Action to allow new users to sign up.
 */
public class MySpaceAction
  extends BaseAction {

  private static final long serialVersionUID = 6558317334878272308L;
  private String myspaceAppApiKey;
  private String myspaceAppSecret;
    private static final String SCHEME_KEY = "scheme";

  /**
   * When method=GET, "input" is returned. Otherwise, "success" is returned.
   *
   * @return cancel, input or success
   */


  public String execute() {
    if (ServletActionContext.getRequest().getMethod().equals("GET")) {

      HttpServletRequest request = ServletActionContext.getRequest();
      HttpSession session = request.getSession();
      OAuth3LeggedScheme scheme = null;
      String verifierParam = request.getParameter("oauth_verifier");
      String tokenParam = request.getParameter("oauth_token");

      /*String accessTokenKey = (String)request.getSession()
        .getAttribute("accessTokenKey");
      String accessTokenSecret = (String)request.getSession()
        .getAttribute("accessTokenSecret");

      if (accessTokenKey == null || accessTokenSecret == null) {
        String requestTokenKey = request.getParameter("oauth_token");
        String oauthVerifier = request.getParameter("oauth_verifier");
        String requestTokenSecret = (String)request.getSession()
          .getAttribute("requestTokenSecret");

        OffsiteContext c2 = new OffsiteContext(getMyspaceAppApiKey(), getMyspaceAppSecret(), requestTokenKey,
                                               requestTokenSecret);
        OAuthToken accessToken = c2.getAccessToken(
          oauthVerifier); // Side effect: sets access token in OffsiteContext object

        accessTokenKey = accessToken.getKey();
        accessTokenSecret = accessToken.getSecret();

        request.getSession().setAttribute("accessTokenKey", accessTokenKey);
        request.getSession().setAttribute("accessTokenSecret", accessTokenSecret);

        OffsiteContext c = new OffsiteContext(getMyspaceAppApiKey(), getMyspaceAppSecret());
	      c.setAccessToken(new OAuthToken(accessTokenKey, accessTokenSecret));

	      // Fetch and display user ID.
	      String id = c.getUserId();

        personManager.myspaceSave(SessionUtil.personLoggedInId(ServletActionContext.getRequest().getSession()),
                                id, accessTokenKey,accessTokenSecret);

      }*/

      if (session != null) {
        scheme = (OAuth3LeggedScheme) session.getAttribute(SCHEME_KEY);
      }

      if (scheme != null && scheme.getRequestToken() != null && tokenParam != null) {
        try {
          scheme.requestAccessToken(tokenParam, verifierParam);
          String socialUid = null;
          Client client = new Client(scheme.getProvider(), scheme);
          Map<String, Request> requests = new HashMap<String, Request>();
          requests.put("viewer", PeopleService.getViewer());
          Map<String, Response> responses = client.send(requests);
          Person viewer = responses.get("viewer").getEntry();
          socialUid = viewer.getId();
          personManager.myspaceSave(SessionUtil.personLoggedInId(ServletActionContext.getRequest().getSession()),
                                socialUid, scheme.getAccessToken().token,scheme.getAccessToken().secret);
        } catch (Exception e) {
          e.printStackTrace();
          return ERROR;
        }
      }
    }
    saveMessage(getText("myspace.successful"));
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


  public void setMyspaceAppApiKey(String myspaceAppApiKey) {
    this.myspaceAppApiKey = myspaceAppApiKey;
  }

  public String getMyspaceAppApiKey() {
    return myspaceAppApiKey;
  }

  public void setMyspaceAppSecret(String myspaceAppSecret) {
    this.myspaceAppSecret = myspaceAppSecret;
  }

  public String getMyspaceAppSecret() {
    return myspaceAppSecret;
  }
}