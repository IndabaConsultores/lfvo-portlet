<%@page import="java.util.HashMap"%>
<%@ include file="/html/init.jsp" %>

<%
HashMap<String, Object> officeInfo = (HashMap)request.getAttribute("officeInfo");
%>

APP MANAGER PORTLET

<%if(officeInfo!=null){ %>

<%=officeInfo.get("name")%>

<portlet:actionURL var="saveInfoUrl" name="saveInfo" ></portlet:actionURL>
<aui:form action="<%=saveInfoUrl%>" method="post" name="fm">
	<aui:input name="color1" value="<%=officeInfo.get("color1")%>"></aui:input>
	<aui:input name="color2" value="<%=officeInfo.get("color2")%>"></aui:input>
	<aui:button-row>
		<aui:button type="submit"></aui:button>
	</aui:button-row>
</aui:form>

<%
}else{
%>
<portlet:renderURL var="reload"></portlet:renderURL>
<aui:button href="<%=reload%>"> Reload</aui:button>
<%}%>




