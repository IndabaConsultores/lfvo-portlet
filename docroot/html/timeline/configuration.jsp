<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@ include file="/html/init.jsp"%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<%  
boolean showInMain_cfg = GetterUtil.getBoolean(portletPreferences.getValue("showInMain", StringPool.TRUE));
%>

<aui:form action="<%= configurationURL %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

    <!-- Preference control goes here -->
    <aui:input name="preferences--showInMain--" type="checkbox" value="<%= showInMain_cfg %>" />

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>