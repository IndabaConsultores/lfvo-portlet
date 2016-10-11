<%@ include file="/html/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "manager");

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("navigation", navigation);

%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon" >
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="appManagerURL" />

		<aui:nav-item
			href="<%=appManagerURL%>"
			label="app_manager"
			selected='<%= navigation.equals("manager") %>'
		/>

		<portlet:renderURL var="appBuilderURL">
			<portlet:param name="navigation" value="build" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%=appBuilderURL%>"
			label="app_builder"
			selected='<%= navigation.equals("build") %>'
		/>
	</aui:nav>

</aui:nav-bar>

<c:choose>
	<c:when test='<%= navigation.equals("manager") %>'>
		<liferay-util:include page="/html/appmanager/app_manager.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/appmanager/build_app.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>
