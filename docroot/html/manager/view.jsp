<%@ include file="/html/init.jsp" %>

<%

PortletURL portletURL = renderResponse.createRenderURL();

%>
<!--
<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon" >
	<aui:nav cssClass="navbar-nav">
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>
-->
<liferay-util:include page="/html/manager/view_items.jsp" servletContext="<%= application %>" />

