<%@ include file="/html/init.jsp" %>

<div class="container-fluid-1280">
	
	<portlet:actionURL name="doDataDiagnosis" var="doDataDiagnosisURL">
		<portlet:param name="redirect" value="<%=currentURL%>"/>
	</portlet:actionURL>
	<aui:form action="<%=doDataDiagnosisURL%>" method="post" name="fm1" >
		<div class="card-horizontal main-content-card">
			Conf para diagnostico
		</div>
		<aui:button-row>
			<aui:button cssClass="btn-lg btn-primary" type="submit" />
		</aui:button-row>
	</aui:form>
	
	
	<portlet:actionURL name="doDataSync" var="doDataSyncURL">
		<portlet:param name="redirect" value="<%=currentURL%>"/>
	</portlet:actionURL>
	<aui:form action="<%=doDataSyncURL%>" method="post" name="fm2" >
		<div class="card-horizontal main-content-card">
			Conf para sync
		</div>
		<aui:button-row>
			<aui:button cssClass="btn-lg btn-primary" type="submit" />
		</aui:button-row>
	</aui:form>
	
</div>