<%@ include file="/html/init.jsp"%>
<%@page import="java.util.HashMap"%>

<%
List<Item> items = (List<Item>)renderRequest.getAttribute("items"); // Listado de objetos filtrados
HashMap<String, String> catById = (HashMap)request.getAttribute("catById"); // Todas las cats (id y nombre)
String selectedCatIds = (String)request.getAttribute("selectedCatIds"); // Ids separados por '/' seleccionados previamente
String image = "";
%>

<div class="col-md-10">

	<% for (Item item : items) { %>	
		<div class="col-md-3">			
		
			<div class="card">
				<div class="aspect-ratio">				
					<%
						List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(item.getItemId());
						if( lfImages.isEmpty()) {
							image = "/o/lfvo-portlet/images/notFound.png";
						} else {
							for(LFImage lfImage : lfImages){
								StringWriter writer = new StringWriter();
								writer.append("data:image/gif;base64,");
								IOUtils.copy(lfImage.getImage().getBinaryStream(), writer);								
								image = writer.toString();
								
								break;
							}
						}						
					%>	
					
					<portlet:renderURL var="viewItemURL">
 						<portlet:param name="mvcPath" value="/html/itemlist/item_detail.jsp" />
 						<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId()) %>" />
						<portlet:param name="redirect" value="<%= currentURL %>"/>				
					</portlet:renderURL>
					<a href="<%=viewItemURL%>" title="<%=item.getName()%>">
						<img src=<%=image%>>
					</a>										
				</div>
			</div>			
		</div>
	<%}%>
</div>

<div class="col-md-2">

	<portlet:actionURL var="filtrar" name="filtrar" ></portlet:actionURL>
	<form id="myForm" action="<%=filtrar%>" method="post">
		<ul class="list-group">
			<% for (String key : catById.keySet()) { 	
				String catName = catById.get(key);	
				%>
			
  				<li class="list-group-item">
  					<input type="checkbox" id="categories" name="categories" value=<%= key %>><%= catName%>
  				</li>  			
  			<% } %>
		</ul>
		
		<input class="btn btn-info btn-sm" type="submit" value="Submit">			
	</form>
</div>

<div style="clear: both"></div>

<aui:script>
$( document ).ready(function() {
	
	var pnamespace = '<portlet:namespace/>';
	
	$('input').each(function() {			
		jQuery(this).attr('id', pnamespace + this.id);
		jQuery(this).attr('name', pnamespace + this.name);
	});
	
	// Ponemos a checked los radio buttons
	var catsChecked = "<%=selectedCatIds%>";	
	$( ".list-group-item :input" ).each(function() {   
	     var currentCat = $( this ).val();
	     if(catsChecked.indexOf(currentCat) !== -1){	    	 
	    	 $(this).attr('checked', 'checked');
	     }
	     
	});
});

function popUp(url ,title, width, height, modal){
	AUI().use('aui-modal', 'aui-io-plugin-deprecated', function(A) {
			popup = Liferay.Util.Window.getWindow({
				dialog : {
					destroyOnHide : true,
					height : height,
					width : width
				},
				title : title
			});
			popup.plug(A.Plugin.IO, {
				uri : url
			});

		});
}
</aui:script> 

