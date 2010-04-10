<%@ include file="/includes/taglibs.jsp" %>
<%--
<c:if test="${pageContext.request.locale.language ne 'en'}">
    <div id="switchLocale"><a href="<c:url value='/?locale=en'/>"><fmt:message
            key="webapp.name"/> in English</a></div>
</c:if>
--%>

<%--<div id="branding">--%>
<table>
<tr valign="top" >
   <td rowspan="2"><a href="<c:url value='/'/>"><img src="/images/ShoutAllLogo.png" alt="<fmt:message key="webapp.name"/>" /></a></td>
    <td width="50%" valign="middle" align="right">
        <c:if test="${isLoggedIn}">
         <b>Welcome,</b> <%= SessionUtil.personLoggedInName(session) %>
        </c:if>
    </td>
</tr>    
<tr valign="bottom">
    <td width="100%" align="right">
        <fmt:message key="webapp.tagline"/>
    </td>
</tr>
</table>
<%--</div>--%>
<%-- Put constants into request scope --%>
<appfuse:constants scope="request"/>

