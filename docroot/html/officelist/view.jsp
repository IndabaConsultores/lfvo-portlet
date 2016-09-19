<%@page import="com.liferay.portal.kernel.model.Group"%>
<%@page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil"%>
<%@ include file="/html/init.jsp"%>

<% 
List<Group> offices = GroupLocalServiceUtil.getGroups(themeDisplay.getCompanyId(), 0, true);
%>


<div class="col-md-12">

	<%
	for(Group office : offices){
	%>

	<div class="col-md-4">
		<div class="card" style="max-width: 300px;">
			<div class="crop-img crop-img-bottom crop-img-center"
				style="height: 150px;">
				<img alt="thumbnail"
					src="<%=request.getContextPath()%>/images/hot_air_ballon.jpg">
			</div>
			<div class="user-icon user-icon-danger user-icon-xxl"
				style="border: 4px solid #FFF; line-height: 120px; margin: -64px auto 0; position: relative;">
				<span>Donos</span>
			</div>
			<div class="card-block" style="text-align: center;">
				<h3 style="margin: 0;"><%=office.getName(locale) %></h3>
				<h5 class="text-default" style="margin-top: 0;">Administrator</h5>
				<p>Algo de información estadística etc...</p>
			</div>
		</div>
	</div>
	<%}%>
</div>