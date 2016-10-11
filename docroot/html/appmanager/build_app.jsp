<%@ include file="/html/init.jsp" %>

<portlet:actionURL name="buildApp" var="buildAppURL">
	<portlet:param name="redirect" value="<%=redirect%>"/>
</portlet:actionURL>

<aui:form method="post" enctype="multipart/form-data" name="fm" action="<%=buildAppURL%>" >

	<div class="container">
		<div class="row">
			<div class="col col-md-12">
				<aui:input name="appName" label="appName">
					<aui:validator name="required"/>
				</aui:input>
			</div>
		</div>

		<div class="row">
			<div class="col col-md-8">
				<aui:input id="iconInput" name="appIcon" type="file" label="icon">
					<aui:validator name="acceptFiles">'png, psd'</aui:validator>
				</aui:input>
			</div>
			<div class="col col-md-4">
				<div class="card img-preview icon" id="icon-preview">
					<img src="/o/lfvo-portlet/images/notFound.png">
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col col-md-8">
				<aui:input id="splashInput" name="appSplash" type="file" label="splash">
					<aui:validator name="acceptFiles">'png, psd'</aui:validator>
				</aui:input>
			</div>
			<div class="col col-md-4">
				<div class="card img-preview splash" id="splash-preview">
					<img src="/o/lfvo-portlet/images/notFound.png">
				</div>
			</div>
		</div>
		<div class="row">
			<aui:button-row>
				<aui:button type="submit" value="Build App"></aui:button>
			</aui:button-row>
		</div>
	</div>

</aui:form>

<style type="text/css">
	.img-preview {
		max-height: 276px;
		max-width: 276px;
	}
	.icon {
		height: 120px;
		width: 120px;
	}
	.splash {
		height: 276px;
		width: 276px;
	}
	.img-preview img {
		object-fit: contain;
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

