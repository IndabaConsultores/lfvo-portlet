<%@page import="net.indaba.lostandfound.service.ItemLocalServiceUtil"%>
<%@page import="net.indaba.lostandfound.model.Item"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/html/init.jsp" %>

<%
Item item = (Item)request.getAttribute("item");
if(item==null)
	item = ItemLocalServiceUtil.createItem(0);
%>
<portlet:actionURL name="editItem" var="editItemURL">
	<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId())%>"/>
	<portlet:param name="redirect" value="<%=currentURL%>"/>
</portlet:actionURL>

<aui:form method="post" name="fm" action="<%=editItemURL%>">

	<aui:input name="name"></aui:input>

	<aui:button-row>
		<aui:button type="submit" value="add"></aui:button>
	</aui:button-row>

</aui:form>
