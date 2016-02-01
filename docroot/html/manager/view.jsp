<%@ include file="/html/init.jsp" %>

<aui:button-row>
	<portlet:renderURL var="addItemURL">
		<portlet:param name="mvcPath" value="/html/manager/edit_item.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>
	<aui:button href="<%= addItemURL.toString() %>" value="add"></aui:button>
</aui:button-row>

<liferay-ui:search-container
	emptyResultsMessage="no-web-content-was-found">
	<liferay-ui:search-container-row className="net.indaba.lostandfound.model.Item" modelVar="item" >
		<liferay-ui:search-container-column-text> adasd</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator  markupView="lexicon" />
</liferay-ui:search-container>