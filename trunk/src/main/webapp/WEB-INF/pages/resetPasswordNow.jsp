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
<s:form name="resetPasswordNowForm" action="changePasswordNow" method="post" validate="true">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='resetPassword.heading'/></h1>
<ul>
    <li>
        <s:password key="password" showPassword="true"
                    theme="xhtml" required="true"
                    cssClass="text medium"/>
        <s:password key="confirmPassword" theme="xhtml"
                    required="true"
                    showPassword="true" cssClass="text medium"/>
    </li>
    <li>
        <s:submit key="button.resetNow" cssClass="button"/>
    </li>
</ul>
</fieldset>
</s:form>
</td>
</tr>
</table>

