<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.StringWriter"%>
<%@page import="net.indaba.lostandfound.model.LFImage"%>
<%@page import="net.indaba.lostandfound.service.LFImageLocalServiceUtil"%>
<%@page import="net.indaba.lostandfound.service.ItemLocalServiceUtil"%>
<%@ include file="/html/init.jsp" %>

<%
long itemId = ParamUtil.getLong(renderRequest, "itemId", 0);
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


	<ul class="list-unstyled row">
	<%
	List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(itemId);
	for(LFImage lfImage : lfImages){
		StringWriter writer = new StringWriter();
		IOUtils.copy(lfImage.getImage().getBinaryStream(), writer);
		request.setAttribute("lfImageId-tmp", String.valueOf(lfImage.getLfImageId()));
		
	%>
		<li class="col-md-2 col-sm-4 col-xs-6 yui3-dd-draggable" data-draggable="true" data-selectable="true">
				
				<liferay-frontend:vertical-card
					actionJsp="/html/manager/image_action.jsp"
					actionJspServletContext="<%= application %>"
					cssClass="entry-display-style"
					imageUrl="<%="data:image/png;base64," + writer.toString()%>"
				>
					<%@ include file="/html/manager/image_vertical_card.jspf" %>
				</liferay-frontend:vertical-card>
		</li>
	<%
	}
	%>
	</ul>
	
	<aui:input name="itemImage" type="file" label="image" />

	<aui:button-row>
		<aui:button type="submit" value='<%=item.getItemId()==0?"add":"save"%>'></aui:button>
		<aui:button type="button" value="cancel" href="<%=redirect %>"></aui:button>
	</aui:button-row>

</aui:form>



<portlet:actionURL name="addItemImage" var="addItemImageURL">
	<portlet:param name="itemId" value="<%=String.valueOf(item.getItemId())%>"/>
	<portlet:param name="redirect" value="<%=currentURL%>"/>
</portlet:actionURL>
<aui:form action="<%= addItemImageURL %>" method="post" name="fm2"
	enctype="multipart/form-data">
	<aui:button-row>
		<aui:button class="aui-button-input" type="button"
			value="save" id="sendImage"/>
	</aui:button-row>
</aui:form>


<script type="text/javascript">
$(':file').change(function(){
    var file = this.files[0];
    name = file.name;
    size = file.size;
    type = file.type;

    if(file.name.length < 1) {
    	alert('what?!');
    }
    else if(file.size > 100000) {
        alert("The file is too big");
    }
    else if(file.type != 'image/png' && file.type != 'image/jpg' && file.type != 'image/gif' && file.type != 'image/jpeg' ) {
        alert("The file does not match png, jpg or gif");
    }
    else { 
    	alert('Sooo good');
        $('#<portlet:namespace/>sendImage') .click(function(){
            var formData = new FormData($('#<portlet:namespace/>fm2')[0]);
            alert('Sooo good 1');
            $.ajax({
                url: '<%= addItemImageURL %>',  //server script to process data
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
                    $("*setIdOfImageInHiddenInput*").val(data.logo_id);
                },
                error: function(xhr, status, error) {
                	  alert(xhr.responseText + " " + error);
                	}
,
                // Form data
                data: formData,
                // Options to tell jQuery not to process data or worry about the content-type
                cache: false,
                contentType: false,
                processData: false
            }, 'json');
        });
    }
});
</script>





<portlet:actionURL name="invokeTaglibDiscussion" var="addMessageURL">
</portlet:actionURL>


<liferay-ui:discussion 
		classPK="<%=item.getItemId()%>" 
		userId="<%=themeDisplay.getUserId() %>" 
		className="net.indaba.lostandfound.model.Item" 
		formAction="<%=addMessageURL %>" 
		redirect="<%=redirect%>" 
		formName="fm3"></liferay-ui:discussion>

