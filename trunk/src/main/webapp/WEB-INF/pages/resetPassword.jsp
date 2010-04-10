<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="com.mystatusonline.webapp.util.RecaptchaHelper" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="resetPassword.title"/></title>
</head>
<body id="resetPassword"/>
<table width="680px">
<tr valign="top" class="padding-right:5px;">
<td>
<s:form name="resetPasswordForm" action="resetPassword" method="post" validate="true">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='resetPassword.heading'/></h1>
<ul>
    <li>
        <s:textfield key="email" cssClass="text medium" label="Account Email"
                     required="true"
                     tabindex="1"/>
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
        <s:submit key="button.reset" cssClass="button"/>
    </li>
</ul>
</fieldset>
</s:form>
</td>
</tr>
</table>

