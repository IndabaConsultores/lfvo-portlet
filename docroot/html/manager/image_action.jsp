<%@ include file="/html/init.jsp"%>

<%
String lfImageId = (String)request.getAttribute("lfImageId-tmp");
String itemIdTmp = (String)request.getAttribute("itemId-tmp");
%>
<portlet:resourceURL var="serveImages">
	<portlet:param name="jspPage" value='<%="/html/manager/item_image_list.jsp"%>'/>
	<portlet:param name="itemId" value="<%=itemIdTmp%>"/>
</portlet:resourceURL>

<portlet:actionURL name='deleteLfImage' var="deleteURL">
	<portlet:param name="lfImageId" value="<%= lfImageId %>" />
</portlet:actionURL>

<liferay-ui:icon-menu direction="left-side"
	icon="<%=StringPool.BLANK%>" markupView="lexicon"
	message="<%=StringPool.BLANK%>" showWhenSingleIcon="<%=true%>">
	<liferay-ui:icon-delete
		trash="<%=false%>" url='<%= "javascript:deleteImage" + lfImageId +"()"%>' />

</liferay-ui:icon-menu>

<script type="text/javascript">
function deleteImage<%= lfImageId %>(){
	$.ajax({
			url: '<%= deleteURL %>',  
	    type: 'POST',
	    // Ajax events
	    success: completeHandler = function(data) {
			console.log("image deleted");
			
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
	    },
	    error: function(xhr, status, error) {
	    	console.log("error deleting image");
	    },
	    // Options to tell jQuery not to process data or worry about the content-type
	    cache: false,
	    contentType: false,
	    processData: false
	}, 'html');
}
</script>
