
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.StringWriter"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="java.util.Enumeration"%>
<%@include file="/html/init.jsp"%>

<%
	Item item = (Item) request.getAttribute("item");
%>

<dl>
	<dt>Name</dt>
	<dd><%=item.getName()%></dd>
	<ul class="list-unstyled row">
	<%
	List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(item.getItemId());
	for(LFImage lfImage : lfImages){
		StringWriter writer = new StringWriter();
		IOUtils.copy(lfImage.getImage().getBinaryStream(), writer);
		request.setAttribute("lfImageId-tmp", String.valueOf(lfImage.getLfImageId()));
		
	%>
		<li class="col-md-2 col-sm-4 col-xs-6 yui3-dd-draggable" data-draggable="true" data-selectable="true">
				
				<liferay-frontend:vertical-card
					actionJsp="/html/manager/image_action.jsp"
					actionJspServletContext="<%= application %>"
					cssClass="entry-display-style"
					imageUrl="<%="data:image/png;base64," + writer.toString()%>"
				>
				</liferay-frontend:vertical-card>
		</li>
	<%
	}
	%>
	</ul>
</dl>

<%
	boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null));
	boolean enableComments = GetterUtil.getBoolean(portletPreferences.getValue("enableComments", null));
%>

<c:if test="<%=themeDisplay.isSignedIn() && (enableRatings || enableComments)%>">
	<liferay-ui:panel-container extended="<%=false%>"
		id="itemCollaborationPanelContainer" persistState="<%=true%>">
		<liferay-ui:panel collapsible="<%=true%>" extended="<%=true%>"
			id="itemCollaborationPanel" persistState="<%=true%>"
			title='<%=LanguageUtil.get(request, "collaboration")%>'>

			<c:if test="<%=enableRatings%>">
				<liferay-ui:ratings className="<%=Item.class.getName()%>"
					classPK="<%=item.getItemId()%>" type="stars" />

				<br />
			</c:if>
			<c:if test="<%=enableRatings%>">
				<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />
				<liferay-ui:discussion classPK="<%=item.getItemId()%>"
					userId="<%=themeDisplay.getUserId()%>"
					className="net.indaba.lostandfound.model.Item"
					formAction="<%=discussionURL%>" redirect="<%=redirect%>"
					formName="fm3"></liferay-ui:discussion>
			</c:if>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</c:if>