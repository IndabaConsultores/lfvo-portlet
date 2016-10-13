<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@ include file="/html/init.jsp"%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<%  
boolean showInMain_cfg = GetterUtil.getBoolean(portletPreferences.getValue("showInMain", StringPool.TRUE));
boolean items_cfg = GetterUtil.getBoolean(portletPreferences.getValue("showItems", StringPool.TRUE));
boolean alerts_cfg = GetterUtil.getBoolean(portletPreferences.getValue("showAlerts", StringPool.TRUE));
%>

<aui:form action="<%= configurationURL %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

    <!-- Preference control goes here -->
    <aui:input name="preferences--showInMain--" type="checkbox" value="<%= showInMain_cfg %>" />
	
	<aui:input name="preferences--showItems--" type="checkbox" value="<%= items_cfg %>" />
	
	<aui:input name="preferences--showAlerts--" type="checkbox" value="<%= alerts_cfg %>" />

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>