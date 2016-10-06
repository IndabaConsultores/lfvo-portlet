<%@page import="com.liferay.portal.kernel.model.Group"%>
<%@page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/html/init.jsp"%>

<%
HashMap<String, Object> officesInfo = (HashMap)request.getAttribute("resultado");
String languageId = LanguageUtil.getLanguageId(request); 

String nombre_es = "";
String nombre_eu = "";
String icon = "";
String numObjetos = "";
String telefono = "";
String email = "";
String url = "";
String siteUrl = "";
Boolean apkCreada = false;
String site_name = "";
%>

<div class="col-md-12">
	<% for (String key : officesInfo.keySet()) {		
		
		HashMap<String, Object> oficinaValues = (HashMap)officesInfo.get(key);
		
		nombre_es = oficinaValues.get("nombre_es").toString();
		nombre_eu = oficinaValues.get("nombre_eu").toString();
		icon = oficinaValues.get("icon").toString();
		if(icon == null || "".equals(icon)){
			icon = "/o/lfvo-portlet/images/notFound.png";
		}
		numObjetos = oficinaValues.get("numObjetos").toString();
		telefono = oficinaValues.get("telefono").toString();
		email = oficinaValues.get("email").toString();
		url = oficinaValues.get("url").toString();
		if("".equals(url.trim())){
			url = "&nbsp;";
		}
		siteUrl = oficinaValues.get("siteUrl").toString();
		apkCreada = (Boolean)oficinaValues.get("apkGenerated");
		site_name = oficinaValues.get("site_name").toString();
	%>	
	
		<div class="col-md-4">
			<div class="card" style="max-width: 300px;">			
				<div class="card_header">					
					<a href=<%=siteUrl %>>
						<img src="<%= icon %>" style="width: 150px; height: 150px; border-radius: 10px 10px 10px 10px;" class="img-thumbnail">
					</a>
				</div>
				<div class="card_content">						
					<% if (languageId.equals("es_ES")){ 
							if("".equals(nombre_es.trim())){ %>
								<h3><%= site_name %></h3>
							<% }else{ %>
								<h3><%= nombre_es %></h3>
							<% } %> 					
    				<% }else{    				 
    						if("".equals(nombre_eu.trim())){ %>
								<h3><%= site_name %></h3>
							<% }else{ %>
								<h3><%= nombre_eu %></h3>
							<% } %> 
   					<% } %>						 
					<p> <%=numObjetos%> <liferay-ui:message key="officeList.encontrados"/> </p>  
					<h6 class="text-default"><liferay-ui:message key="officeList.telefono"/>: <%=telefono%></h6>
					<h6 class="text-default"><liferay-ui:message key="officeList.email"/>: <%=email%></h6>
					<h6 class="text-default"><%=url%> </h6>
					<h6 class="text-default"><liferay-ui:message key="officeList.apk"/>: 
					<% if (apkCreada) { %>
						<a href="<portlet:resourceURL>
							<portlet:param name="groupId" value="<%=key%>"/>
						</portlet:resourceURL>"><liferay-ui:message key="officeList.download"/></a>
					<% } else {%>
						<liferay-ui:message key="officeList.noGenerado"/>
					<% } %>
					</h6>
				</div>
			</div>
		</div>		
	<%}%>
</div>
<div style="clear: both"></div>
