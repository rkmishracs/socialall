<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/taglibs.jsp" %>
<head><title>Thanks for your input</title></head>
<body id="facebookregistered"/>
<div align="left">
<h3><fmt:message key="facebook.successful"/></h3>
<br/>
<input onclick="window.opener.location.reload(1); window.close();" type="button" value="Close" />
</div>

<%--http://localhost:8080//facebookregistered.html#fname=_opener&%7B%22t%22%3A3%2C%22h%22%3A%22fbCancelLogin%22%2C%22sid%22%3A%220.595%22%7D--%>