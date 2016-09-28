<%@page import="net.indaba.lostandfound.util.LFVOConstants"%>
<%@ include file="/html/init.jsp" %>

<%
Item item = null;
if(itemId==0){
	item = ItemLocalServiceUtil.createItem(0);
}
else{
	item = ItemLocalServiceUtil.getItem(itemId);
}	
%>

<portlet:actionURL name="addOrUpdateItem" var="editItemURL">
	<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId())%>"/>
	<portlet:param name="redirect" value="<%=redirect%>"/>
</portlet:actionURL>

<aui:form method="post" name="fm" action="<%=editItemURL%>" 
	enctype="multipart/form-data">
	<aui:input name="itemId" value="<%=item.getItemId()%>" type="hidden"></aui:input>
	<aui:input name="name" value="<%=item.getName()%>"></aui:input>
	
	<liferay-ui:asset-categories-error />
	<liferay-ui:asset-tags-error />
	<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="guestbookCategorizationPanel" persistState="<%= true %>" title="categorization">
		<aui:fieldset>
			<liferay-ui:asset-categories-selector className="net.indaba.lostandfound.model.Item" classPK="<%=item.getItemId()%>"/>
		</aui:fieldset>
	</liferay-ui:panel>

	<liferay-util:include page="/html/manager/item_image_list.jsp" servletContext="<%=application%>" />
	
	<aui:input name="itemImage" type="file" label="image" />

	<aui:button-row>
		<aui:button type="submit" value='<%=item.getItemId()==0?"add":"save"%>'></aui:button>
		<aui:button type="button" value="cancel" href="<%=redirect %>"></aui:button>
	</aui:button-row>

</aui:form>


<%if(itemId!=0){ %>


<portlet:resourceURL var="serveImages">
	<portlet:param name="jspPage" value='<%="/html/manager/item_image_list.jsp"%>'/>
	<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId())%>"/>
</portlet:resourceURL>


<script type="text/javascript">
$(':file').change(function(){
    var file = this.files[0];
    name = file.name;
    size = file.size;
    type = file.type;
	console.log("File selected");
    if(file.name.length < 1) {
    	alert('what?!');
    }
    else if(file.size > 1000000) {
        alert("The file is too big");
    }
    else if(file.type != 'image/png' && file.type != 'image/jpg' && file.type != 'image/gif' && file.type != 'image/jpeg' ) {
        alert("The file does not match png, jpg or gif");
    }
    else { 
    	console.log("File looks great!");
   		var formData = new FormData($('#<portlet:namespace/>fm')[0]);
		$.ajax({
       		url: '<%= editItemURL %>',  //server script to process data
            type: 'POST',
            xhr: function() {  // custom xhr
                myXhr = $.ajaxSettings.xhr();
                /*if(myXhr.upload){ // if upload property exists
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false); // progressbar
                }*/
                return myXhr;
            },
            // Ajax events
            success: completeHandler = function(data) {
            	
            	$.ajax({
               		url: '<%= serveImages %>',  //server script to process data
                    type: 'POST',
                    // Ajax events
                    success: completeHandler = function(data) {
                    	 $('#item-image-list').html(data);
                    },
                    error: function(xhr, status, error) {
                   		alert(xhr.responseText + " " + error);
                    },
                    // Options to tell jQuery not to process data or worry about the content-type
                    cache: false,
                    contentType: false,
                    processData: false
                }, 'html');
            	
            	
                /*
                * Workaround for Chrome browser // Delete the fake path
                */
                if(navigator.userAgent.indexOf('Chrome')) {
                    var catchFile = $(":file").val().replace(/C:\\fakepath\\/i, '');
                }
                else {
                    var catchFile = $(":file").val();
                }
                var writeFile = $(":file");
                writeFile.html(writer(catchFile));
                
                
                
            },
            error: function(xhr, status, error) {
           		alert(xhr.responseText + " " + error);
            },
            // Form data
            data: formData,
            // Options to tell jQuery not to process data or worry about the content-type
            cache: false,
            contentType: false,
            processData: false
        }, 'json');

    }
});
</script>
<%}%>


<%
if(item.getItemId()!=0){
%>

<portlet:actionURL name="invokeTaglibDiscussion" var="addMessageURL">
</portlet:actionURL>


<liferay-ui:discussion 
		classPK="<%=item.getItemId()%>" 
		userId="<%=themeDisplay.getUserId() %>" 
		className="net.indaba.lostandfound.model.Item" 
		formAction="<%=addMessageURL %>" 
		redirect="<%=redirect%>" 
		formName="fm3"></liferay-ui:discussion>

<%}%>