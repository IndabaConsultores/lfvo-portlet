<%@page import="com.liferay.trash.kernel.util.TrashUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ include file="/html/init.jsp"%>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
Item item = null;
if (row != null) {
	item = (Item)row.getObject();
}
%>

<liferay-ui:icon-menu direction="left-side"
	icon="<%=StringPool.BLANK%>" markupView="lexicon"
	message="<%=StringPool.BLANK%>" showWhenSingleIcon="<%=true%>">
	
	<portlet:renderURL var="editItemURL">
		<portlet:param name="mvcPath" value="<%=ItemManagerPortlet.PATH_EDIT_ITEM %>" />
		<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId()) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>
	<liferay-ui:icon
			message="edit"
			url="<%= editItemURL %>"
		/>
	
	<portlet:actionURL name='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "moveItemToTrash" : "deleteItem" %>' var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(item.getGroupId()) %>" />
			<portlet:param name="itemId" value="<%= String.valueOf(item.getItemId())%>" />
		</portlet:actionURL>
	<liferay-ui:icon-delete
	trash="<%=TrashUtil.isTrashEnabled(scopeGroupId)%>" url="<%=deleteURL%>" />

</liferay-ui:icon-menu>
