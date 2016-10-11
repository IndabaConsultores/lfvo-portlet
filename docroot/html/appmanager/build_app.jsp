<%@ include file="/html/init.jsp" %>

<portlet:actionURL name="buildApp" var="buildAppURL">
	<portlet:param name="redirect" value="<%=redirect%>"/>
</portlet:actionURL>

<aui:form method="post" enctype="multipart/form-data" name="fm" action="<%=buildAppURL%>" >

	<aui:input name="appName" label="appName" />

	<aui:input id="iconInput" name="appIcon" type="file" label="icon" />

	<div id="icon-preview"></div>

	<aui:input id="splashInput" name="appSplash" type="file" label="splash" />

	<div id="splash-preview"></div>
	
	<aui:button-row>
		<aui:button type="submit" value="Build App"></aui:button>
	</aui:button-row>

</aui:form>

<style>
.img-preview {
	object-fit: contain;
	max-height: 276px;
	max-width: 276px;
}
</style>

<script type="text/javascript">

var _URL = window.URL || window.webkitURL;

function imagePreview(input){
	if (input.id.includes('icon')) {
		var previewDivId = '#icon-preview';
		var height = 192;
		var width = 192;
	} else {
		var previewDivId = '#splash-preview';
		var height = 2208;
		var width = 2208;
	}
	
	var file = input.files[0];
	name = file.name;
	size = file.size;
	type = file.type;
	
	if(file.name.length < 1) {
		alert('what?!');
	}
	else if(file.size > 1000000) {
		alert("The file is too big");
	}
	else if(file.type != 'image/png' ) {
		alert("The file does not match png");
	}
	else {
		console.log("File looks great!");
		
		var image = new Image();
		image.onload = function() {
			if (image.width<width || image.height<height) {
				$(':submit').prop('disabled', true);
				console.log('Image size too small');
			} else {
				$(':submit').prop('disabled', false);
			}
			$(previewDivId).html('<div class="card"><img class="img-preview" src="' + image.src + '"</div>');
		}
		image.src = _URL.createObjectURL(file);
	}
}

$(':file').change(function() {
	imagePreview(this);
});

</script>
