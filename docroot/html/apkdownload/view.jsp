<%@ include file="/html/init.jsp"%>


<portlet:resourceURL var="iconUrl">
	<portlet:param name="resource" value="icon"/>
</portlet:resourceURL>
<portlet:resourceURL var="apkUrl">
	<portlet:param name="resource" value="apk"/>
</portlet:resourceURL>

<% 
Boolean apkCreated = (Boolean) request.getAttribute("apkCreated");
if (apkCreated) { %>

<div class="col-md-3 ">
	<div class="card card-rounded"> 
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
			<div class="divider"></div>
			<img class="apk-cqr" src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=<%=apkUrl%>"/>
			<i class="icon-mobile"></i>
			<span>&nbsp;QR Code</span> 
		</div> 
	</div>
</div>
<div style="clear: both"></div>

<%} else {%>
<%}%>