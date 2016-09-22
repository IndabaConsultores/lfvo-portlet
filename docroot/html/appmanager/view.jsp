<%@page import="java.util.HashMap"%>
<%@ include file="/html/init.jsp" %>

<%
HashMap<String, Object> officeInfo = (HashMap)request.getAttribute("officeInfo");
String selectedColor1 = officeInfo.get("color1").toString();
String selectedColor2 = officeInfo.get("color2").toString();
String icono = officeInfo.get("icon").toString();
%>

<h1>APP MANAGER PORTLET</h1>

<%if(officeInfo!=null){ %>

<%=officeInfo.get("name")%>

<style>
.palette-item-inner {
    display: block;
    height: 16px;
    width: 16px;
	border: 1px solid #666;
}
.palette-item {
    display: inline-block;
    padding: 0;	
	border: 1px solid transparent;
}
</style>

<portlet:actionURL var="saveInfoUrl" name="saveInfo" ></portlet:actionURL>
<aui:form action="<%=saveInfoUrl%>" method="post" name="fm" enctype="multipart/form-data">

	<liferay-ui:message key="appManager.color1" />
	<div id="myColorPalette_1">
		<ul class="inlineList_1">
    		<li id="color1_1" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#9FC6E7" onclick="return false;" title="#9FC6E7"></a>	
			</li>
			<li id="color1_2" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#5484ED" onclick="return false;" title="#5484ED"></a>	
			</li>
			<li id="color1_3" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#A4BDFC" onclick="return false;" title="#A4BDFC"></a>	
			</li>
			<li id="color1_4" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#51B749" onclick="return false;" title="#51B749"></a>	
			</li>
			<li id="color1_5" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#FBD75B" onclick="return false;" title="#FBD75B"></a>	
			</li>
			<li id="color1_6" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#FFB878" onclick="return false;" title="#FFB878"></a>	
			</li>
			<li id="color1_7" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#FF887C" onclick="return false;" title="#FF887C"></a>	
			</li>
			<li id="color1_8" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#DC2127" onclick="return false;" title="#DC2127"></a>	
			</li>
			<li id="color1_9" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#DBADFF" onclick="return false;" title="#DBADFF"></a>	
			</li>
			<li id="color1_10" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#E1E1E1" onclick="return false;" title="#E1E1E1"></a>	
			</li>
		</ul>
	</div>
	<aui:input type="hidden" name="color1" value="<%=officeInfo.get("color1")%>"></aui:input>

	<liferay-ui:message key="appManager.color2" />
	<div id="myColorPalette_2">
		<ul class="inlineList_2">
    		<li id="color2_1" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#9FC6E7" onclick="return false;" title="#9FC6E7"></a>	
			</li>
			<li id="color2_2" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#5484ED" onclick="return false;" title="#5484ED"></a>	
			</li>
			<li id="color2_3" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#A4BDFC" onclick="return false;" title="#A4BDFC"></a>	
			</li>
			<li id="color2_4" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#51B749" onclick="return false;" title="#51B749"></a>	
			</li>
			<li id="color2_5" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#FBD75B" onclick="return false;" title="#FBD75B"></a>	
			</li>
			<li id="color2_6" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#FFB878" onclick="return false;" title="#FFB878"></a>	
			</li>
			<li id="color2_7" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#FF887C" onclick="return false;" title="#FF887C"></a>	
			</li>
			<li id="color2_8" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#DC2127" onclick="return false;" title="#DC2127"></a>	
			</li>
			<li id="color2_9" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#DBADFF" onclick="return false;" title="#DBADFF"></a>	
			</li>
			<li id="color2_10" class="palette-item">
				<a href="#" class="palette-item-inner" style="background-color:#E1E1E1" onclick="return false;" title="#E1E1E1"></a>	
			</li>
		</ul>
	</div>	
	<aui:input type="hidden" name="color2" value="<%=officeInfo.get("color2")%>"></aui:input>
		
	<aui:input label="appManager.telefono" name="phoneNumber" value="<%=officeInfo.get("phoneNumber")%>"></aui:input>
	
	<aui:input label="appManager.email" name="emailAddress" value="<%=officeInfo.get("emailAddress")%>"></aui:input>
	
	<aui:input type="hidden" name="icon" value="<%=officeInfo.get("icon")%>"></aui:input>
	<aui:input name="itemImage" type="file" label="image" />	
	<img id="iconImage">	
	
	<aui:input label="appManager.descripcion" name="description" value="<%=officeInfo.get("description")%>" type="textarea"></aui:input>
	
	<aui:button-row>
		<aui:button type="submit"></aui:button>
	</aui:button-row>
	
</aui:form>

<%}else{%>

	<portlet:renderURL var="reload"></portlet:renderURL>
	<aui:button href="<%=reload%>"> Reload</aui:button>

<%}%>

<aui:script>
$( document ).ready(function() {
		
	// A) Seleccionamos el color 1 correspondiente en la paleta
	var selectedColor1 = "<%=selectedColor1%>";	
	$(".inlineList_1 li a.palette-item-inner").each(function(index) {		
		var hexColor = $(this).attr('title');						
		if(hexColor === selectedColor1){						
			$(this).css('background-image', 'url(' + "<%=request.getContextPath()%>/images/tick.png" + ')');
		}
	})
	
	// B) Seleccionamos el color 2 correspondiente en la paleta
	var selectedColor2 = "<%=selectedColor2%>";	
	$(".inlineList_2 li a.palette-item-inner").each(function(index) {		
		var hexColor = $(this).attr('title');						
		if(hexColor === selectedColor2){						
			$(this).css('background-image', 'url(' + "<%=request.getContextPath()%>/images/tick.png" + ')');
		}
	})
	
	// C) Pintamos el icono
	var icono = "<%=icono%>";
	$("#iconImage").attr("src", icono);	
});

// Al cambiar una nueva imagen la mostramos en preview
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
    	
    	//var preview = document.querySelector('img');    	
    	var reader  = new FileReader();
    	reader.addEventListener("load", function () {
    		//preview.src = reader.result;
    		$("#iconImage").attr("src", reader.result);	
    	}, false);

    	if (file) {
    		reader.readAsDataURL(file);
    	}    	
    }
});

// Al cambiar el color de la paleta 1
$(".inlineList_1 li a.palette-item-inner").click(function() {
	
	// Quitamos la imagen de fondo 'tick' si la hubiera a todos los colores
	$(".inlineList_1 li a.palette-item-inner").each(function(index) {			
		$(this).css('background-image', 'none');		
	})
	
	// Ponemos el 'tick' de fondo
	$(this).css('background-image', 'url(' + "<%=request.getContextPath()%>/images/tick.png" + ')');
	
	// Guardamos el valor en el campo del formulario
	var pnamespace = '<portlet:namespace/>';	
	$("#" + pnamespace + "color1").val( $(this).attr('title') );	
});


// Al cambiar el color de la paleta 2
$(".inlineList_2 li a.palette-item-inner").click(function() {
	
	// Quitamos la imagen de fondo 'tick' si la hubiera a todos los colores
	$(".inlineList_2 li a.palette-item-inner").each(function(index) {			
		$(this).css('background-image', 'none');		
	})
	
	// Ponemos el 'tick' de fondo
	$(this).css('background-image', 'url(' + "<%=request.getContextPath()%>/images/tick.png" + ')');
	
	// Guardamos el valor en el campo del formulario
	var pnamespace = '<portlet:namespace/>';	
	$("#" + pnamespace + "color2").val( $(this).attr('title') );	
});
</aui:script>