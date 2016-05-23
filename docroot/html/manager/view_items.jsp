<%@ include file="/html/init.jsp" %>

<%
List<Item> items = (List<Item>)renderRequest.getAttribute("items");
%>

<liferay-ui:search-container
	emptyResultsMessage="no-item-was-found" >
	<liferay-ui:search-container-results results="<%=items%>"/>
	<liferay-ui:search-container-row className="net.indaba.lostandfound.model.Item" modelVar="item" >
		<liferay-ui:search-container-column-text>
			
			<portlet:renderURL var="editItemURL">
				<portlet:param name="mvcPath" value="<%=ItemManagerPortlet.PATH_EDIT_ITEM %>" />
				<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId()) %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>
			<a href="<%=editItemURL%>"><%=item.getName()%></a>
			
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-text>
			<liferay-ui:asset-categories-summary classPK="<%=item.getItemId()%>" className="<%=Item.class.getName()%>"/>			
		</liferay-ui:search-container-column-text>
		
		<liferay-ui:search-container-column-jsp
			cssClass="list-group-item-field"
			path="/html/manager/item_action.jsp"
		/>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator  markupView="lexicon" />
</liferay-ui:search-container>

<aui:button-row>
	<portlet:renderURL var="addItemURL">
		<portlet:param name="mvcPath" value="<%=ItemManagerPortlet.PATH_EDIT_ITEM %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>
	<aui:button href="<%= addItemURL.toString() %>" value="add"></aui:button>
</aui:button-row>
<liferay-frontend:add-menu>
<liferay-frontend:add-menu-item url="hfjk" title="gkhg"/>
</liferay-frontend:add-menu>