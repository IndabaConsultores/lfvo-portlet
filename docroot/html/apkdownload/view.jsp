<%@ include file="/html/init.jsp"%>


<portlet:resourceURL var="iconUrl">
	<portlet:param name="resource" value="icon"/>
</portlet:resourceURL>
<portlet:resourceURL var="apkUrl">
	<portlet:param name="resource" value="apk"/>
</portlet:resourceURL>

<style>
.apk-icon {
	display: block;
	margin: auto;
	object-fit: cover;
	max-height: 110px;
	max-width: 110px;
	border: solid DarkSlateGray 1px;
	border-radius: 25%;
}
</style>

<% 
Boolean apkCreated = (Boolean) request.getAttribute("apkCreated");
if (apkCreated) { %>
<div class="card card-rounded" style="max-width: 225px; max-height: 225px;">
	<div class="card-row" style="text-align:center">
		<h3>Deskargatu aplikazioa</h3>
	</div>
	<div class="divider"></div>
	<div class="card-row-padded" style="text-align:center">
		<a href="<%=apkUrl%>">
			<img class="apk-icon" src="<%=iconUrl%>" alt="apk-icon"/>
			<i class="icon-download-alt"></i>
			<span>&nbsp;Download</span>
		</a>
	</div>
</div>
<%} else {%>
<%}%>