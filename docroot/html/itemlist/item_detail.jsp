<%@page import="net.indaba.lostandfound.util.LFVOConstants"%>

<%@page import="com.liferay.asset.kernel.model.AssetCategory"%>
<%@page import="com.liferay.asset.kernel.model.AssetEntry"%>
<%@page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil"%>

<%@ include file="/html/init.jsp" %>

<%
double lat;
double lng;
Item item = null;
if(itemId==0){
	item = ItemLocalServiceUtil.createItem(0);
	lat = 0;
	lng = 0;
}
else{
	item = ItemLocalServiceUtil.getItem(itemId);
	lat = item.getLat();
	lng = item.getLng();
}
String image = "";

String languageId = LanguageUtil.getLanguageId(request);
String tabs = "";
if(languageId.equals("es_ES")){
	tabs = "Artículo,Descripción";
}else{
	tabs = "Artikulu,Deskribapena";
}
%>

<div class="panel panel-default">	
	<div class="panel-body">
		<div class="col-md-12">	
		
			<!-- 1) INFORMACION -->		
			<div class="col-md-5">
					
				<liferay-ui:tabs names="<%=tabs%>" refresh="false" tabsValues="<%=tabs%>">
    				<liferay-ui:section>
			     		 			
       		 			<%
							List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(item.getItemId());
							if( lfImages.isEmpty()) {
								image = "/o/lfvo-portlet/images/notFound.png";
						%>					
						<div class="card">
							<div class="aspect-ratio">				
								<img src=<%=image%>><img src=<%=image%>>
							</div>		
						</div>				
		
						<%} else {
							for(LFImage lfImage : lfImages){
								StringWriter writer = new StringWriter();
								writer.append("data:image/gif;base64,");
								IOUtils.copy(lfImage.getImage().getBinaryStream(), writer);								
								image = writer.toString();
						%>						
								<div class="card">
									<div class="aspect-ratio">				
										<img src=<%=image%>><img src=<%=image%>>
									</div>		
								</div>					
						<%		break;
							}
						}
						%>
    				</liferay-ui:section>
    				
    				<liferay-ui:section>
    				
    					<div class="card ">		 
							<div class="card-row-padded"> 
								<h2><%=item.getName()%></h2>
								<div class="divider"></div>
								<p><%=item.getDescription()%></p>    
							</div> 
						</div>
						
    				</liferay-ui:section>    
				</liferay-ui:tabs>				
			</div>
				
			<!-- 2) MAPA -->
			<div class="col-md-7">				
				<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />
				<script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet.js"></script>
				
				<div id="mapid" style="height: 450px;"></div>
				<div style="clear: both"></div>
				
				<script>
					var currentLangCode = '<%=pageContext.getRequest().getLocale().getLanguage()%>';
					
					var lat = '<%=lat%>';
					var lng = '<%=lng%>';
					
					var mymap = L.map('mapid').setView([Number(lat),Number(lng)], 13);

					L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
						attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://cloudmade.com">CloudMade</a>',
						maxZoom: 18
					}).addTo(mymap);

					L.control.scale().addTo(mymap);		
	 				L.marker([Number(lat), Number(lng)]).addTo(mymap);
				</script>
				<div style="clear: both"></div>			
			</div>			
		</div>
	</div>
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