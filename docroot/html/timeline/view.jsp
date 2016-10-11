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

String lat = "";
String lng = "";
String date = "";
String image = "";

%>

<link rel="stylesheet" href="/o/lfvo-portlet/css/reset.css"> <!-- CSS reset -->
<link rel="stylesheet" href="/o/lfvo-portlet/css/style.css"> <!-- Resource style -->
<script src="/o/lfvo-portlet/js/modernizr.js"></script> <!-- Modernizr -->

<section class="cd-horizontal-timeline">
	<div class="timeline">
		<div class="events-wrapper">
			<div class="events">
				<ol>				
					<%					
					boolean primero = false;
					for (Date key : keys) {
												
						SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
						String fecha = dt1.format(key); 
						
						if(!primero){ %>							
							<li><a href="#0" data-date="<%=fecha%>" class="selected"><%=fecha%></a></li>
							<% primero = true; %>
						<%}else {%>
							<li><a href="#0" data-date="<%=fecha%>"><%=fecha%></a></li>
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
						String fechaE = miElement.get("date").toString();
						String imagen = miElement.get("image").toString();
						String desc = miElement.get("desc").toString();
				%>	
						<h2><%=titulo%></h2>
						<em><%=fechaE%></em>
<%-- 						<p><%=desc%><p> --%>
<%-- 						<img src="<%=imagen%>">						 --%>
<!-- 						<br> -->

						<div class="card card-horizontal card-rounded">
							<div class="card-row card-row-valign-top">
								<div class="card-col-field">
									<img src="<%=imagen%>" style="border-radius: 4px 0 0 4px;width: 150px;">
								</div>
								<div class="card-col-content card-col-gutters">
									<h4>Categoria</h4>
									<div class="divider"></div>
									<p><%=desc%></p>
								</div>
							</div>
						</div>
						
						<%}%>
					</li>				
			<%}%>			
		</ol>
	</div> <!-- .events-content -->
</section>
<div style="clear: both"></div>

<script src="/o/lfvo-portlet/js/timeline.js"></script> <!-- Resource jQuery -->