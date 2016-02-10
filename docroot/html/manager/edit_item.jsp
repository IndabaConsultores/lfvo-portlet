<%@page import="net.indaba.lostandfound.service.ItemLocalServiceUtil"%>
<%@page import="net.indaba.lostandfound.model.Item"%>
<%@ include file="/html/init.jsp" %>

<%
long itemId = ParamUtil.getLong(renderRequest, "itemId", 0);
Item item = null;
if(itemId==0){
	item = ItemLocalServiceUtil.createItem(0);
}
else{
	item = ItemLocalServiceUtil.getItem(itemId);
}	
%>
<portlet:actionURL name="editItem" var="editItemURL">
	<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId())%>"/>
	<portlet:param name="redirect" value="<%=redirect%>"/>
</portlet:actionURL>

<aui:form method="post" name="fm" action="<%=editItemURL%>">
	<aui:input name="itemId" value="<%=item.getItemId()%>" type="hidden"></aui:input>
	<aui:input name="name" value="<%=item.getName()%>"></aui:input>

	<aui:button-row>
		<aui:button type="submit" value="add"></aui:button>
		<aui:button type="button" value="cancel" href="<%=redirect %>"></aui:button>
	</aui:button-row>

</aui:form>
