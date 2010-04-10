<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ page import="com.mystatusonline.webapp.util.RecaptchaHelper" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="signup.title"/></title>
</head>
<body id="siteLogin"/>
<table width="680px">
<tr valign="top" class="padding-right:5px;">
<td>
<s:form name="loginForm" action="attempLogin" method="post" validate="true">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='login.heading'/></h1>
<ul>
    <c:if test="${param.error != null}">
        <li class="error">
            <img src="${ctx}/images/iconWarning.gif"
                 alt="<fmt:message key='icon.warning'/>" class="icon"/>
            <fmt:message key="errors.password.mismatch"/>
        </li>
    </c:if>
    <li>
        <s:textfield key="username" cssClass="text medium"
                     required="true" label="Username"
                     tabindex="1"/>
    </li>
    <li>
        <s:password key="password" showPassword="true"
                theme="xhtml" required="true" label="Password"
                cssClass="text medium" tabindex="2"/>
    </li>
    <li>
        <s:checkbox type="checkbox" class="checkbox"
               key="rememberMe" tabindex="3" label="Remember Me"/>
    </li>
    <li>
        <s:submit key="button.login" cssClass="button"/>
    </li>
    <li>
        <fmt:message key="login.passwordHint"/>
    </li>
</ul>
</fieldset>
</s:form>
</td>
<td>
<s:form name="signupForm" action="signup" method="post" validate="true">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='signup.heading'/></h1>
<ul>
<li>
    <fmt:message key="signup.message"/>
</li>
<li>
    <s:textfield key="person.username" cssClass="text medium" required="true"/>
</li>
<li>
    <s:password key="person.password" showPassword="true"
                theme="xhtml" required="true"
                cssClass="text medium"/>
    <s:password key="person.confirmPassword" theme="xhtml"
                required="true"
                showPassword="true" cssClass="text medium"/>
</li>

<li>
    <s:textfield key="person.firstName" theme="xhtml" required="true"
                         cssClass="text medium"/>
    <s:textfield key="person.lastName" theme="xhtml" required="true"
                         cssClass="text medium"/>
</li>

<li>
    <s:textfield key="person.email" theme="xhtml" required="true"
                         cssClass="text medium"/>
</li>
<li>
    <s:textfield key="person.phoneNumber" theme="xhtml" required="false"
                         cssClass="text medium"/>
</li>
<li>
    <%
        // create recaptcha without <noscript> tags
        ReCaptcha captcha = RecaptchaHelper.getReCaptchaInstance();
        String captchaScript = captcha.createRecaptchaHtml(request.getParameter("error"), null);
        out.print(captchaScript);
    %>
</li>

<li>
    <s:submit key="button.register" cssClass="button"/>
</li>
</ul>
</fieldset>
</s:form>
</td>
</tr>
</table>
<%@ include file="/scripts/login.js" %>
<script type="text/javascript">
    Form.focusFirstElement(document.forms["loginForm"]);
</script>
<script type="text/javascript">
    jQuery(function($) {
      $j('#signup_person_phoneNumber').mask('(999) 999-9999');
});</script>
