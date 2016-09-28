<%@page import="com.liferay.portal.kernel.model.Group"%>
<%@page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/html/init.jsp"%>

<% 
List<Group> offices = GroupLocalServiceUtil.getGroups(themeDisplay.getCompanyId(), 0, true);
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
%>

<div class="col-md-12">

	<% for(Group office : offices){		
		
		HashMap<String, Object> oficinaValues = (HashMap)officesInfo.get(String.valueOf(office.getGroupId()));		
		if(oficinaValues!= null){
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
		%>	
	
			<div class="col-md-4">
				<div class="card" style="max-width: 300px;">			
					<div class="card_header">					
						<a href=<%=siteUrl %>>
							<img src="<%= icon %>" style="width: 150px; border-radius: 10px 10px 10px 10px; " class="img-thumbnail">
						</a>
					</div>
					<div class="card_content">						
						<% if (languageId.equals("es_ES")){ %>
    						<h3><%= nombre_es %></h3>
    					<% }else{ %>
    						<h3><%= nombre_eu %></h3>
   						 <% } %>						 
						<p> <%=numObjetos%> <liferay-ui:message key="officeList.encontrados"/> </p>  
						<h6 class="text-default">Telefono: <%=telefono%></h6>
						<h6 class="text-default">Email: <%=email%></h6>
						<h6 class="text-default"><%=url%> </h6>
					</div>
				</div>
			</div>	
				
		<%}%>
	<%}%>
</div>