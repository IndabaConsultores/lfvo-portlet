<%@ include file="/html/init.jsp"%>

<%
String lfImageId = (String)request.getAttribute("lfImageId-tmp");
%>

<liferay-ui:icon-menu direction="left-side"
	icon="<%=StringPool.BLANK%>" markupView="lexicon"
	message="<%=StringPool.BLANK%>" showWhenSingleIcon="<%=true%>">
	
	<portlet:actionURL name='deleteLfImage' var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="lfImageId" value="<%= lfImageId %>" />
		</portlet:actionURL>
	<liferay-ui:icon-delete
	trash="<%=false%>" url="<%=deleteURL%>" />

</liferay-ui:icon-menu>
