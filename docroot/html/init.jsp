<%@page import="com.liferay.portal.kernel.portlet.PortletURLUtil"%>
<%@page import="javax.portlet.PortletURL"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@taglib uri="http://liferay.com/tld/security"
	prefix="liferay-security"%>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend"%>

<%@page import="java.util.List"%>
<%@page import="java.io.StringWriter"%>
<%@page import="org.apache.commons.io.IOUtils"%>

<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>

<%@page import="net.indaba.lostandfound.portlet.ItemManagerPortlet"%>
<%@page import="net.indaba.lostandfound.model.Item"%>
<%@page import="net.indaba.lostandfound.model.LFImage"%>
<%@page import="net.indaba.lostandfound.service.LFImageLocalServiceUtil"%>
<%@page import="net.indaba.lostandfound.service.ItemLocalServiceUtil"%>


<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
	PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);
	String currentURL = currentURLObj.toString();

	String redirect = ParamUtil.get(request, "redirect", "");
%>

<%
long itemId = ParamUtil.getLong(request, "itemId");
%>