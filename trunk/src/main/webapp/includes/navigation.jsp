<%@ include file="/includes/taglibs.jsp" %>
<%@ page import="com.mystatusonline.webapp.util.SessionUtil" %>
<%
    boolean updatestatus = request.getRequestURI().toLowerCase().contains("updatestatus");
    pageContext.setAttribute("updatestatus",updatestatus);

    boolean mainMenu = request.getRequestURI().toLowerCase().contains("mainmenu");
    pageContext.setAttribute("mainMenu",mainMenu);

    boolean socialNetworks = request.getRequestURI().toLowerCase().contains("socialnetworks");
    pageContext.setAttribute("socialNetworks",socialNetworks);

%>
<ul id="menu">
<c:if test="${!isLoggedIn}">
    <li><a href="siteLogin.html" title="">Login</a></li>
</c:if>
<c:if test="${isLoggedIn}">
    <c:if test="${updatestatus}">
    <li><a href="updateStatus.html" title="" class="current">Update Status Everywhere</a></li>
    </c:if>
    <c:if test="${!updatestatus}">
    <li><a href="updateStatus.html" title="">Update Status Everywhere</a></li>
    </c:if>


    <c:if test="${socialNetworks}">
    <li><a href="socialNetworks.html" title="" class="current">My Social Networks</a></li>
    </c:if>
    <c:if test="${!socialNetworks}">
    <li><a href="socialNetworks.html" title="">My Social Networks</a></li>
    </c:if>

    <c:if test="${mainMenu}">
    <li><a href="mainMenu.html" title="" class="current">My Information</a></li>
    </c:if>
    <c:if test="${!mainMenu}">
    <li><a href="mainMenu.html" title="">My Information</a></li>
    </c:if>

<li><a href="logout.jsp" title="">Logout</a></li>
</c:if>
</ul>