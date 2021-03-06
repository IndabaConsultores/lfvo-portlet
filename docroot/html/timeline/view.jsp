<%@page import="com.liferay.portal.kernel.model.Group"%>
<%@page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil"%>
<%@page import="java.util.HashMap"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import='java.util.Date' %>
<%@ page import='java.text.SimpleDateFormat' %>
<%@ include file="/html/init.jsp"%>

<%
HashMap<String, ArrayList<HashMap<String, Object>>> resultado = (HashMap)request.getAttribute("resultado");
ArrayList<Date> keys = (ArrayList<Date>)request.getAttribute("orderedKeys");

String languageId = LanguageUtil.getLanguageId(request); 
%>

<script src="/o/lfvo-portlet/js/modernizr.js"></script> <!-- Modernizr -->
<script src="/o/lfvo-portlet/js/timeline.js"></script>

<!-- 1.A) TIMELINE DE ALERTAS NO VACIO -->
<% if(keys != null && !keys.isEmpty()){ %>

<section class="cd-horizontal-timeline">
	<div class="horizontaltimeline">
		<div class="events-wrapper">
			<div class="events">
				<ol>				
					<%					
					boolean primero = false;
					for (Date key : keys) {
												
						SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
						String fecha = dt1.format(key); 
						
						String timeLineTitle = "";
						if(languageId.equals("es_ES")){
							timeLineTitle = fecha;
						}else{
							SimpleDateFormat dt2 = new SimpleDateFormat("yyyy/MM/dd");
							timeLineTitle = dt2.format(key);
						}
						
						if(!primero){ %>							
							<li><a href="#0" data-date="<%=fecha%>" class="selected"><%=timeLineTitle%></a></li>
							<% primero = true; %>
						<%}else {%>
							<li><a href="#0" data-date="<%=fecha%>"><%=timeLineTitle%></a></li>
						<%}
					}%>					
				</ol>

				<span class="filling-line" aria-hidden="true"></span>
			</div> <!-- .events -->
		</div> <!-- .events-wrapper -->
			
		<ul class="cd-timeline-navigation">
			<li><a href="#0" class="prev inactive">Prev</a></li>
			<li><a href="#0" class="next">Next</a></li>
		</ul> <!-- .cd-timeline-navigation -->
	</div> <!-- .timeline -->

	<!-- Esta parte la oculamos, ya que la mostramos junto al mapa -->
	<div style="display: none;">
	<div class="events-content">
		<ol>
		
			<%					
			boolean primeroDos = false;
			for (Date key : keys) {
												
				SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = dt1.format(key); 
						
				if(!primeroDos){ 
			%>							
					<li class="selected" data-date="<%=fecha%>">						
					<% primeroDos = true; %>
				<%}else {%>
					<li data-date="<%=fecha%>">
				<%}
				
					ArrayList<HashMap<String, Object>> items = resultado.get(key);
					for (int i = 0; i < items.size(); i++) {
							
						HashMap<String, Object> miElement = items.get(i);
							
						String titulo = miElement.get("name").toString();
						String imagen = miElement.get("image").toString();
						String desc = miElement.get("desc").toString();
						
						String lat = miElement.get("lat").toString();
						String lng = miElement.get("lng").toString();
						
						String type = miElement.get("type").toString();
				%>

						<div class="alerta-timeline">
							<div class="card card-horizontal card-rounded">
								<div class="card-row card-row-valign-top">
									<div class="card-col-field">
										<img onclick="javascript:posicionObjeto('<%=lat%>','<%=lng%>')" src="<%=imagen%>" style="border-radius: 4px 0 0 4px;width: 150px;">
										<%if( "".equals(type) || "office".equals(type) ){ %>										
											<span class="sticker sticker-bottom sticker-info">O</span>
										<%} else if ("lost".equals(type)) { %>
											<span class="sticker sticker-bottom sticker-danger">L</span>
										<%} else if ("found".equals(type)) {%>
											<span class="sticker sticker-bottom sticker-success">F</span>
										<%} %>										
									</div>
									<div class="card-col-content card-col-gutters">
										<h4><%=titulo%></h4>
										<div class="divider"></div>
										<p><%=desc%></p>
									</div>
								</div>
							</div>
						</div>						
						
						<%}%>
					</li>				
			<%}%>			
		</ol>
	</div> <!-- .events-content -->
	</div>
</section>

<% } else { %>

<!-- 1.B) TIMELINE DE ALERTAS VACIO -->
<section class="cd-horizontal-timeline">
	<div class="horizontaltimeline">
		<div class="events-wrapper">
			<div class="events">
				<ol>
					<div style="display: none;">
						<li><a href="#0" data-date="16/01/2014" class="selected"></a></li>
					</div>		
				</ol>
				<span class="filling-line" aria-hidden="true"></span>
			</div> <!-- .events -->
		</div> <!-- .events-wrapper -->
			
		<ul class="cd-timeline-navigation">
			<li><a href="#0" class="prev inactive">Prev</a></li>
			<li><a href="#0" class="next">Next</a></li>
		</ul> <!-- .cd-timeline-navigation -->
	</div> <!-- .timeline -->
	
	<div style="display: none;">
		<div class="events-content">
			<ol>
				<li class="selected" data-date="16/01/2014">
					<div class="alert alert-info">
						<p><liferay-ui:message key="timeline.vacio"/></p>
					</div>
				</li>		
			</ol>
		</div> <!-- .events-content -->
	</div>
</section>

<% } %>

<!-- Script para el detalle de lo seleccionado en el Timeline -->
<script>
$( document ).ready(function() { 

	// TIMELINE: Obtener el html del seleccionado al entrar
	var a = $(".events-content .selected").html();
	$("#miSelected").html(a);	
   
	// TIMELINE: Si pulsamos una fecha, obtenemos el html del seleccionado
	$(".events a").click(function() {		
		var b = $(this).attr("data-date");		
		var c = $(".events-content ol [data-date='" + b + "']").html();		
		$("#miSelected").html(c);
		
	});	
});
</script>

<!-- 2) MAPA DE ALERTAS Y DETALLE DE LOS ITEMS POR DIA -->
<link rel="stylesheet" type="text/css" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
<link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/MarkerCluster.css" />
<link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/MarkerCluster.Default.css" />
 
<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js'></script>
<script type='text/javascript' src='http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js'></script>
<script type='text/javascript' src='http://cdnjs.cloudflare.com/ajax/libs/leaflet.markercluster/0.4.0/leaflet.markercluster.js'></script>
 
<div class="col-md-8">
	<div id="mapid" style="height: 450px;"></div>
</div>

<div class="col-md-4">
	<div id="miSelected"></div>
</div>
<div style="clear: both"></div>

<!-- Script para el Mapa -->
<script>
	var currentLangCode = '<%=pageContext.getRequest().getLocale().getLanguage()%>';
	var data = <%=pageContext.getRequest().getAttribute("popUps")%>;
	
	var mymap = L.map('mapid').setView([43.306163, -1.972854], 13);

	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery � <a href="http://cloudmade.com">CloudMade</a>',
		maxZoom: 18
	}).addTo(mymap);

	L.control.scale().addTo(mymap);
	var markers = L.markerClusterGroup();	
	for (var i = 0; i < data.length; i++) {
	
		var popupHTML = '<div style="text-align:center"><b>' + data[i].name  +'</b><br/><img class="popup-img" style="max-width:75px !important;" src="' + data[i].image + '"/>';		
		if( currentLangCode == 'es'){
			popupHTML = popupHTML + '<br/><b>' + data[i].date_es  +'</b></div>';
		}else{
			popupHTML = popupHTML + '<br/><b>' + data[i].date_eu  +'</b></div>';
		}
	 	
	 	var marker = L.marker([Number(data[i].lat), Number(data[i].lng)]).bindPopup(popupHTML).on('click', function(e) {this.openPopup();});
	 	markers.addLayer(marker);	 	
	}
	
	mymap.addLayer(markers);
	
	function posicionObjeto(lat, lng) {		
		mymap.panTo(new L.LatLng(Number(lat), Number(lng)));
 	}	
</script>
<div style="clear: both"></div>