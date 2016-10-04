<%@page import="net.indaba.lostandfound.util.LFVOConstants"%>

<%@page import="com.liferay.asset.kernel.model.AssetCategory"%>
<%@page import="com.liferay.asset.kernel.model.AssetEntry"%>
<%@page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil"%>



<%@ include file="/html/init.jsp" %>

<%
Item item = null;
if(itemId==0){
	item = ItemLocalServiceUtil.createItem(0);
}
else{
	item = ItemLocalServiceUtil.getItem(itemId);
}
String image = "";
%>

<!-- NOMBRE -->
<%=item.getName()%>
<br>

<!-- CATEGORIAS -->
<%
AssetEntry ast = AssetEntryLocalServiceUtil.fetchEntry("net.indaba.lostandfound.model.Item", item.getPrimaryKey());
List<AssetCategory> listaCatsDos = ast.getCategories();
for(AssetCategory cat : listaCatsDos){%>				
	<%=cat.getName() %>
	<br>
<%}%>

<!-- IMAGENES -->
<div class="col-md-12">			
		<%
		List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(item.getItemId());
		if( lfImages.isEmpty()) {
			image = "/o/lfvo-portlet/images/notFound.png";
		%>	
			<div class="col-md-3">		
				<div class="card">
					<div class="aspect-ratio">				
						<img src=<%=image%>><img src=<%=image%>>
					</div>		
				</div>
			</div>		
		
		<%} else {
			for(LFImage lfImage : lfImages){
				StringWriter writer = new StringWriter();
				writer.append("data:image/gif;base64,");
				IOUtils.copy(lfImage.getImage().getBinaryStream(), writer);								
				image = writer.toString();
		%>				
				<div class="col-md-3">		
					<div class="card">
						<div class="aspect-ratio">				
							<img src=<%=image%>><img src=<%=image%>>
						</div>		
					</div>
				</div>
			<%}
		}%>	
</div>
<div style="clear: both"></div>

<!--  DISCUSION -->

<%
if(item.getItemId()!=0){
%>

<portlet:actionURL name="invokeTaglibDiscussion" var="addMessageURL">
</portlet:actionURL>


<liferay-ui:discussion 
		classPK="<%=item.getItemId()%>" 
		userId="<%=themeDisplay.getUserId() %>" 
		className="net.indaba.lostandfound.model.Item" 
		formAction="<%=addMessageURL %>" 
		redirect="<%=redirect%>" 
		formName="fm3"></liferay-ui:discussion>

<%}%>
