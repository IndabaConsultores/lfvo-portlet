<%@ include file="/html/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "items");

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("navigation", navigation);

%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon" >
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewItemsURL" />

		<aui:nav-item
			href="<%=viewItemsURL%>"
			label="items"
			selected='<%= navigation.equals("items") %>'
		/>

		<portlet:renderURL var="adminURL">
			<portlet:param name="navigation" value="admin" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%=adminURL%>"
			label="administration"
			selected='<%= navigation.equals("admin") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= navigation.equals("items") %>'>
		<liferay-util:include page="/html/manager/view_items.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/manager/view_admin.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>
