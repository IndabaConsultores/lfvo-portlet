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

<!-- 1) MAPA DE ALERTAS -->
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet.js"></script>

<div id="mapid" style="height: 450px;"></div>
<script>
	var currentLangCode = '<%=pageContext.getRequest().getLocale().getLanguage()%>';
	var data = <%=pageContext.getRequest().getAttribute("popUps")%>;
	
	var mymap = L.map('mapid').setView([43.306163, -1.972854], 13);

	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://cloudmade.com">CloudMade</a>',
		maxZoom: 18
	}).addTo(mymap);

	L.control.scale().addTo(mymap);

	for (var i = 0; i < data.length; i++) {
	
		var popupHTML = '<div style="text-align:center"><b>' + data[i].name  +'</b><br/><img class="popup-img" style="max-width:75px !important;" src="' + data[i].image + '"/>';		
		if( currentLangCode == 'es'){
			popupHTML = popupHTML + '<br/><b>' + data[i].date_es  +'</b></div>';
		}else{
			popupHTML = popupHTML + '<br/><b>' + data[i].date_eu  +'</b></div>';
		}
		
	 	L.marker([Number(data[i].lat), Number(data[i].lng)]).addTo(mymap).bindPopup(popupHTML).on('click', function(e) {this.openPopup();});
	}
</script>


<!-- 2) TIMELINE DE ALERTAS -->
<% if(keys != null && !keys.isEmpty()){ %>

<link rel="stylesheet" href="/o/lfvo-portlet/css/reset.css"> <!-- CSS reset -->
<link rel="stylesheet" href="/o/lfvo-portlet/css/style.css"> <!-- Resource style -->
<script src="/o/lfvo-portlet/js/modernizr.js"></script> <!-- Modernizr -->
<script src="/o/lfvo-portlet/js/timeline.js"></script>

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
				%>

						<div class="alerta-timeline">
							<div class="card card-horizontal card-rounded">
								<div class="card-row card-row-valign-top">
									<div class="card-col-field">
										<img src="<%=imagen%>" style="border-radius: 4px 0 0 4px;width: 150px;">
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
</section>
<% } %>

<div style="clear: both"></div>