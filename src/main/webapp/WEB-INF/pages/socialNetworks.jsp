<%@ page import="com.mystatusonline.webapp.util.SessionUtil" %>
<%@ page import="com.mystatusonline.webapp.util.RequestUtil" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/common/taglibs.jsp" %>
<%
    String appUrl = RequestUtil.getAppURL(request);
    pageContext.setAttribute("appUrl",appUrl);
%>
<head>
    <title><fmt:message key="socialNetworks.title"/></title>
</head>
<body id="socialNetworks"/>
<h2><fmt:message key='socialNetwork.explain'/></h2>
<table width="680px">
<tr valign="top" class="padding-right:5px;">
<td width="50%">
<s:form name="facebookForm" action="facebookSave" method="post">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='facebook.heading'/></h1>
<ul>
    <c:if test="${facebook.id == null}">
        <li>
            <fb:login-button onlogin="facebook_onloginCallback();"/>
        </li>
    </c:if>
    <c:if test="${facebook.id != null && facebook.facebookSessionKey != null}">
        <li>
            <h2><fmt:message key="facebook.alreadyRegistered" /></h2>
            <fb:login-button onlogin="facebook_onloginCallback();"/>
        </li>
    </c:if>
</ul>
</fieldset>
</s:form>
</td>
<td width="50%">
<s:form name="twitterForm" action="twitterSave" method="post">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='twitter.heading'/></h1>
<ul>
    <c:if test="${twitter.id == null}">
        <li>
            <input type="image" alt="Sign in with Twitter" src="/images/SignInTwitter.png"/>
        </li>
    </c:if>
    <c:if test="${twitter.id != null && twitter.OAuthAccessToken != null}">
        <li>
            <h2><fmt:message key="twitter.alreadyRegistered" /></h2>
            <input type="image" alt="Sign in with Twitter" src="/images/SignInTwitter.png"/>
        </li>
    </c:if>
</ul>
</fieldset>
</s:form>
</td>
</tr>
<tr valign="top" class="padding-right:5px;">
<td width="50%">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='googlebuzz.heading'/></h1>
<img src="/images/google-buzz.jpg" alt="Google buzz"/>
<br/>
</fieldset>
</td>
<td width="50%">
<s:form name="myspaceForm" action="myspaceSave" method="post">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='myspace.heading'/></h1>
<ul>
    <c:if test="${myspace.id == null}">
        <li>
            <input type="image" alt="Sign in with Twitter" src="/images/SignInMyspace.png"/>
        </li>
    </c:if>
    <c:if test="${myspace.id != null && myspace.OAuthAccessToken != null}">
        <li>
            <h2><fmt:message key="myspace.alreadyRegistered" /></h2>
            <input type="image" alt="Sign in with MySpace" src="/images/SignInMyspace.png"/>
        </li>
    </c:if>
</ul>
</fieldset>
</s:form>
</td>
</tr>
</table>

<script src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php" type="text/javascript"></script>
<script type="text/javascript">

FB.init("<%= session.getAttribute("facebookAppApiKey") %>", "<%= appUrl %>/facebook.html" ,
            {permsToRequestOnConnect : "offline_access,read_stream,publish_stream"}
        );


function facebook_onloginCallback(){
  FB.Connect.ifUserConnected(facebook_loggedIn(),facebook_notLoggedIn());
}

function facebook_loggedIn(){
    FB.Connect.logoutAndRedirect("<%= appUrl %>/socialNetworks.html?message=forceLogin");
}
function facebook_notLoggedIn(){
}

</script>