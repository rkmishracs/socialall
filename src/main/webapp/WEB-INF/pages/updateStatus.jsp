<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/common/taglibs.jsp" %>
<style>
    label{
	display:block;
	font-size:14px;
	}
textarea{
	width:600px;
	height:60px;
	border:2px solid #ccc;
	padding:3px;
	color:#555;
	font:16px Arial, Helvetica, sans-serif;
	}
form div{position:relative;margin:1em 0;}
form .counter{
	position:relative;
	right:0;
	top:0;
	font-size:20px;
	font-weight:bold;
	color:#54C571;
	}
form .warning{color:#600;}
form .exceeded{color:#e00;}          

</style>

<%--http://cssglobe.com/lab/charcount/01.html--%>
<script type="text/javascript">
	$j(document).ready(function(){
		//default usage
		//$j("#status").charCount();
		//custom usage
		$j("#status").charCount({
			allowed: 140,
			warning: 20,
			counterText: 'Twitter Characters left: '
		});
	});

    function postNow(){
        var msg = $j("#status").val();
        <c:if test="${twitter != null}">
        $j("#twitter_img").css("display","");
        $j.post("postStatus.html", { status: msg , network: "twitter" },
        function(data){
            $j("#twitter_img").css("display","none");
           //document.write(data);
           //TODO: how do you handle error?
        });
        </c:if>
        <c:if test="${facebook != null}">
        $j("#facebook_img").css("display","");
        $j.post("postStatus.html", { status: msg , network: "facebook" },
        function(data){
            $j("#facebook_img").css("display","none");
           //document.write(data);
           //TODO: how do you handle error?
        });
        </c:if>
        <c:if test="${myspace != null}">
        $j("#myspace_img").css("display","");
        $j.post("postStatus.html", { status: msg , network: "myspace" },
        function(data){
            $j("#myspace_img").css("display","none");
           //document.write(data);
           //TODO: how do you handle error?
        });
        </c:if>
    }
</script>

<s:form name="updateStatisForm" action="postStatus" method="post" validate="true">
<fieldset style="padding-bottom: 0">
<h1><fmt:message key='updatestatus.heading'/></h1>
<ul>
    <li>
        <div>
        	<label for="status">Type your message</label>
        	<textarea id="status" name="status"></textarea>
        </div>
    </li>
    <li>
        <div id="statusErrors"></div>
    </li>
    <li>
        <div align="center">
        <%--<s:submit key="button.updateStatus" cssClass="button large"/>--%>
        <c:if test="${facebook == null && twitter == null && myspace == null}">
        <fmt:message key="socialNetwork.none" />
        <input type="button" value="POST" disabled="true"/>
        </c:if>
        <c:if test="${facebook != null || twitter != null || myspace != null}">
        <input type="button" onclick="postNow();" value="POST"/>
        </c:if>
        </div>
    </li>
</ul>
<div id="twitter_img" style="display:none;"><img src="/images/ajax-loader.gif" alt="Twitter Updating" />Updating Twitter</div>
<div id="facebook_img" style="display:none;"><img src="/images/ajax-loader.gif" alt="Facebook Updating" />Updating Facebook</div>
<div id="myspace_img" style="display:none;"><img src="/images/ajax-loader.gif" alt="MySpace Updating" />Updating MySpace</div>
</fieldset>
</s:form>
