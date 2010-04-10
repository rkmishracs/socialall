<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="heading" content="<fmt:message key='mainMenu.heading'/>"/>
    <meta name="menu" content="MainMenu"/>
</head>
<s:form name="updateForm" action="updatePerson" method="post" validate="true">
<fieldset style="padding-bottom: 0">
<ul>
<li>
    <s:hidden key="person.id"/>
    <s:hidden key="person.version"/>
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
    <s:submit key="button.save" cssClass="button"/>
</li>
</ul>
</fieldset>
</s:form>
<script type="text/javascript">
    jQuery(function($) {
      $j('#updatePerson_person_phoneNumber').mask('(999) 999-9999');
});</script>