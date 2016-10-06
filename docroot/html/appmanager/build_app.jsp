<%@ include file="/html/init.jsp" %>

<form>
    
    <liferay-portlet:actionURL name="buildApp" var="buildAppVar" />
   	<div class="my-boton">
    	<a class="btn btn-info btn-sm" href="<%=buildAppVar %>">Build App</a>
    </div>
    
</form>