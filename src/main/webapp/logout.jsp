<%@ include file="/common/taglibs.jsp" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page
        import="org.springframework.security.ui.rememberme.TokenBasedRememberMeServices" %>
<%@ page import="com.mystatusonline.webapp.util.CookieUtil" %>

<%
    CookieUtil.removeCookies(response);
    if (request.getSession(false) != null) {
        session.invalidate();
    }
    Cookie terminate = new Cookie(
            TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,
            null);
    String contextPath = request.getContextPath();
    terminate.setPath(
            contextPath != null && contextPath.length() > 0 ? contextPath :
            "/");
    terminate.setMaxAge(0);
    response.addCookie(terminate);
%>

<c:redirect url="/siteLogin.html"/>