<%@ include file="/html/init.jsp" %>

<ul class="list-unstyled row" id="item-image-list">
	<%
	List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(itemId);
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
					<%@ include file="/html/manager/image_vertical_card.jspf" %>
					<%@ page %>
				</liferay-frontend:vertical-card>
		</li>
	<%
	}
	%>
</ul>