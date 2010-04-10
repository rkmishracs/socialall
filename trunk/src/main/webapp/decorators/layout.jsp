<%@ page import="com.mystatusonline.model.Person" %>
<%@ page import="com.mystatusonline.webapp.util.SessionUtil" %>
<%@ page import="com.mystatusonline.webapp.util.RequestUtil" %>
<%
    boolean isLoggedIn = SessionUtil.isPersonLoggedIn(session);
    pageContext.setAttribute("isLoggedIn",isLoggedIn);

    String appUrl = RequestUtil.getAppURL(request);    
    pageContext.setAttribute("appUrl",appUrl);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
    <%@ include file="/includes/meta.jsp" %>
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

    <link rel="stylesheet" type="text/css" media="all"
          href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>"/>
    <link rel="stylesheet" type="text/css" media="print"
          href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>"/>
    <script type="text/javascript"
            src="<c:url value='/scripts/jquery.js'/>"></script>
    <script type="text/javascript"
            src="<c:url value='/scripts/prototype.js'/>"></script>
    <script type="text/javascript"
            src="<c:url value='/scripts/scriptaculous.js'/>"></script>
    <script>
        var $j = jQuery.noConflict();
    </script>
    <script type="text/javascript"
            src="<c:url value='/scripts/charCount.js'/>"></script>
    <script type="text/javascript"
            src="<c:url value='/scripts/global.js'/>"></script>
    <script type="text/javascript"
            src="<c:url value='/scripts/jquery.maskedinput-1.2.2.min.js'/>"></script>
    <decorator:head/>
</head>

<body<decorator:getProperty property="body.id"
                            writeEntireProperty="true"/><decorator:getProperty
        property="body.class" writeEntireProperty="true"/>>
    <div id="page">
    <table id="page-container" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="2" id="page-header">
            <div class="clearfix" style="padding:0px;">    
            <%@ include file="/includes/header.jsp"%>
            </div>
        </td> 
    </tr>
    <tr>
        <td id="nav-container">
            <%@ include file="/includes/navigation.jsp" %>
        </td>
    </tr>
    <tr>
        <td id="content-container">
            <%@ include file="/includes/messages.jsp" %>
            <h1><decorator:getProperty property="meta.heading"/></h1>                        
            <div id="content" class="clearfix">
            <div id="main">
            <decorator:body/>
            </div>
            </div>
        </td>
    </tr>
    <tr>
        <div id="footer" class="clearfix">
        <td colspan="2" id="page-footer">
            <%@ include file="/includes/footer.jsp" %>
        </td>
        </div>
    </tr>
    </table>
    </div>

    <!--- USERVOICE -->
    <script type="text/javascript">
    var uservoiceOptions = {
      /* required */
      key: 'shoutall',
      host: 'shoutall.uservoice.com',
      forum: '42853',
      showTab: true,
      /* optional */
      alignment: 'left',
      background_color:'#f00',
      text_color: 'white',
      hover_color: '#06C',
      lang: 'en'
    };

    function _loadUserVoice() {
      var s = document.createElement('script');
      s.setAttribute('type', 'text/javascript');
      s.setAttribute('src', ("https:" == document.location.protocol ? "https://" : "http://") + "cdn.uservoice.com/javascripts/widgets/tab.js");
      document.getElementsByTagName('head')[0].appendChild(s);
    }
    _loadSuper = window.onload;
    window.onload = (typeof window.onload != 'function') ? _loadUserVoice : function() { _loadSuper(); _loadUserVoice(); };
    </script>

    <!-- GOOGLE ANALYTICS -->

    <script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
    try {
    var pageTracker = _gat._getTracker("UA-436529-15");
    pageTracker._trackPageview();
    } catch(err) {}</script>

</body>
</html>
