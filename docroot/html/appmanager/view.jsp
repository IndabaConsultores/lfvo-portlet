<%@page import="java.util.HashMap"%>
<%@ include file="/html/init.jsp" %>

<%
String languageId = LanguageUtil.getLanguageId(request); 
HashMap<String, Object> officeInfo = (HashMap)request.getAttribute("officeInfo");

String title_cas = "";
String title_eus = "";
String color1 = "";
String color2 = "";
String phoneNumber = "";
String emailAddress = "";
String icon = "";
String description_cas = "";
String description_eus = "";
String url = "";
%>

<%if(officeInfo!=null){
		
	HashMap<String, Object> title =  (HashMap)officeInfo.get("title");
	if(title != null && title.get("es") != null){
		title_cas = title.get("es").toString();
	}

	if(title != null && title.get("eu") != null){
		title_eus = title.get("eu").toString();
	}
	
	if(officeInfo.get("color1") != null){
		color1 = officeInfo.get("color1").toString();
	}

	if(officeInfo.get("color2") != null){
		color2 = officeInfo.get("color2").toString();
	}

	if(officeInfo.get("phoneNumber") != null){
		phoneNumber = officeInfo.get("phoneNumber").toString();
	}
	
	if(officeInfo.get("emailAddress") != null){
		emailAddress = officeInfo.get("emailAddress").toString();
	}	
	
	if(officeInfo.get("icon") != null){
		icon = officeInfo.get("icon").toString();
	}
	
 	HashMap<String, Object> description =  (HashMap)officeInfo.get("description");
 	if(description != null && description.get("es") != null){
 		description_cas = description.get("es").toString();
 	}

 	if(description != null && description.get("eu") != null){
 		description_eus = description.get("eu").toString();
 	}
	
	if(officeInfo.get("url") != null){
		url = officeInfo.get("url").toString();
	}	
%>

<portlet:actionURL var="saveInfoUrl" name="saveInfo" ></portlet:actionURL>

<div class="page-header">
	<% if (languageId.equals("es_ES")){ %>
    	<h1>Lost And Found APP Manager: <%=title_cas%></h1>
    <% }else{ %>
    	<h1>Lost And Found APP Manager: <%=title_eus%></h1>
    <% } %>
</div>

<div class="form-style">
<form id="myForm" action="<%=saveInfoUrl%>" method="post" name="fm" enctype="multipart/form-data">
    
    <div class="control-group">
        <label class="control-label" for="title_cas"><liferay-ui:message key="appManager.titulo_Es"/>:</label>
        <div class="controls">        
            <input name="title_cas" id="title_cas" type="text" size="30" required value="<%=title_cas%>">            
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label" for="title_eus"><liferay-ui:message key="appManager.titulo_Eu"/>:</label>
        <div class="controls">
            <input name="title_eus" id="title_eus" type="text" size="30" required value="<%=title_eus%>">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label" for="color1"><liferay-ui:message key="appManager.color1"/>:</label>
		<!-- <div class="controls"> -->
            <div id="myColorPalette_1">
				<ul class="inlineList_1">
    				<li id="color1_1" class="palette-item">
						<a href="#" class="palette-item-inner color1" onclick="return false;" title="#9FC6E7"></a>	
					</li>
					<li id="color1_2" class="palette-item">
						<a href="#" class="palette-item-inner color2" onclick="return false;" title="#5484ED"></a>	
					</li>
					<li id="color1_3" class="palette-item">
						<a href="#" class="palette-item-inner color3" onclick="return false;" title="#A4BDFC"></a>	
					</li>
					<li id="color1_4" class="palette-item">
						<a href="#" class="palette-item-inner color4" onclick="return false;" title="#51B749"></a>	
					</li>
					<li id="color1_5" class="palette-item">
						<a href="#" class="palette-item-inner color5" onclick="return false;" title="#FBD75B"></a>	
					</li>
					<li id="color1_6" class="palette-item">
						<a href="#" class="palette-item-inner color6" onclick="return false;" title="#FFB878"></a>	
					</li>
					<li id="color1_7" class="palette-item">
						<a href="#" class="palette-item-inner color7" onclick="return false;" title="#FF887C"></a>	
					</li>
					<li id="color1_8" class="palette-item">
						<a href="#" class="palette-item-inner color8" onclick="return false;" title="#DC2127"></a>	
					</li>
					<li id="color1_9" class="palette-item">
						<a href="#" class="palette-item-inner color9" onclick="return false;" title="#DBADFF"></a>	
					</li>
					<li id="color1_10" class="palette-item">
						<a href="#" class="palette-item-inner color10" onclick="return false;" title="#E1E1E1"></a>	
					</li>
				</ul>
			</div>
		<!-- </div> -->
    </div>
    <input type="hidden" id= "color1" name="color1" value="<%=color1%>">
    
    <div class="control-group">
        <label class="control-label" for="color2"><liferay-ui:message key="appManager.color2"/>:</label>
			<!-- <div class="controls"> -->
            <div id="myColorPalette_2">
				<ul class="inlineList_2">
    				<li id="color2_1" class="palette-item">
						<a href="#" class="palette-item-inner color1" onclick="return false;" title="#9FC6E7"></a>	
					</li>
					<li id="color2_2" class="palette-item">
						<a href="#" class="palette-item-inner color2" onclick="return false;" title="#5484ED"></a>	
					</li>
					<li id="color2_3" class="palette-item">
						<a href="#" class="palette-item-inner color3" onclick="return false;" title="#A4BDFC"></a>	
					</li>
					<li id="color2_4" class="palette-item">
						<a href="#" class="palette-item-inner color4" onclick="return false;" title="#51B749"></a>	
					</li>
					<li id="color2_5" class="palette-item">
						<a href="#" class="palette-item-inner color5" onclick="return false;" title="#FBD75B"></a>	
					</li>
					<li id="color2_6" class="palette-item">
						<a href="#" class="palette-item-inner color6" onclick="return false;" title="#FFB878"></a>	
					</li>
					<li id="color2_7" class="palette-item">
						<a href="#" class="palette-item-inner color7" onclick="return false;" title="#FF887C"></a>	
					</li>
					<li id="color2_8" class="palette-item">
						<a href="#" class="palette-item-inner color8" onclick="return false;" title="#DC2127"></a>	
					</li>
					<li id="color2_9" class="palette-item">
						<a href="#" class="palette-item-inner color9" onclick="return false;" title="#DBADFF"></a>	
					</li>
					<li id="color2_10" class="palette-item">
						<a href="#" class="palette-item-inner color10" onclick="return false;" title="#E1E1E1"></a>	
					</li>
				</ul>
			</div>
		<!-- </div> -->
    </div>
   	<input type="hidden" id= "color2" name="color2" value="<%=color2%>">
   	   	
   	<div class="control-group">
        <label class="control-label" for="phoneNumber"><liferay-ui:message key="appManager.telefono"/>:</label>
        <div class="controls">
            <input name="phoneNumber" id="phoneNumber" type="text" value="<%=phoneNumber%>">
        </div>
    </div>
 	
    <div class="control-group">
        <label class="control-label" for="email"><liferay-ui:message key="appManager.email"/>:</label>
        <div class="controls">
            <input name="emailAddress" id="emailAddress" type="email" size="30" value="<%=emailAddress%>">
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label" for="itemImage"><liferay-ui:message key="appManager.icono"/>:</label>
        <div class="controls">
        	<img id="iconImage" class="img-thumbnail">
			
			<!-- <input id="itemImage" name="itemImage" type="file" />  -->
			<br>            
            <div class="fileUpload btn btn-default btn-sm">
 				<span><liferay-ui:message key="appManager.seleccionar"/></span>    		
 				<input id="itemImage" name="itemImage" class="upload" type="file">
			</div>            
            
        </div>
    </div>
    <input type="hidden" id="icon" name="icon" value="<%=icon%>">
    
    <div class="control-group">
        <label class="control-label" for="description_cas"><liferay-ui:message key="appManager.descripcion_Es"/>:</label>        
        <div class="controls">
            <textarea rows="4" cols="50" name="description_cas" id="description_cas"><%=description_cas%></textarea>            
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label" for="description_eus"><liferay-ui:message key="appManager.descripcion_Eu"/>:</label>        
        <div class="controls">            
            <textarea rows="4" cols="50" name="description_eus" id="description_eus"><%=description_eus%></textarea>
        </div>
    </div>
    
 	<div class="control-group">
        <label class="control-label" for="url"><liferay-ui:message key="appManager.url"/>:</label>
        <div class="controls">
            <input name="url" id="url" type="text" size="30" value="<%=url%>">
        </div>
    </div>

	<div class="my-boton">
    	<input class="btn btn-info btn-sm" type="submit" value="Submit">
    </div>
    
    <liferay-portlet:actionURL name="buildApp" var="buildAppVar" />
   	<div class="my-boton">
    	<a class="btn btn-info btn-sm" href="<%=buildAppVar %>">Build App</a>
    </div>

</form>
</div>

<%}else{%>

	<portlet:renderURL var="reload"></portlet:renderURL>
	<aui:button href="<%=reload%>"> Reload</aui:button>

<%}%>

<aui:script>

var currentLangCode = '<%=pageContext.getRequest().getLocale().getLanguage()%>';

$( document ).ready(function() {
	
	var pnamespace = '<portlet:namespace/>';
	
	$('input').each(function() {			
		jQuery(this).attr('id', pnamespace + this.id);
		jQuery(this).attr('name', pnamespace + this.name);
	});
	
	$('textarea').each(function() {			
		jQuery(this).attr('id', pnamespace + this.id);
		jQuery(this).attr('name', pnamespace + this.name);
	});	
	
	// A) Seleccionamos el color 1 correspondiente en la paleta
	var selectedColor1 = "<%=color1%>";
	var i = 1;
	$(".inlineList_1 li a.palette-item-inner").each(function(index) {		
		var hexColor = $(this).attr('title');						
		if(hexColor === selectedColor1){
			$(this).removeClass( "color" + i );
			$(this).addClass( "color" + i + "-tick" );			
		}
		i = i + 1;
	})
	
	// B) Seleccionamos el color 2 correspondiente en la paleta
	var selectedColor2 = "<%=color2%>";
	var z = 1;
	$(".inlineList_2 li a.palette-item-inner").each(function(index) {		
		var hexColor = $(this).attr('title');						
		if(hexColor === selectedColor2){
			$(this).removeClass( "color" + z );
			$(this).addClass( "color" + z + "-tick" );			
		}
		z = z + 1;
	})
	
	// C) Pintamos el icono
	var icono = "<%=icon%>";
	$("#iconImage").attr("src", icono);
});

// Al cambiar el color de la paleta 1
$(".inlineList_1 li a.palette-item-inner").click(function() {
	
	// Quitamos la imagen de fondo 'tick' al elemento que la tuviera
	var i = 1;
	$(".inlineList_1 li a.palette-item-inner").each(function(index) {		
		if ( $( this ).hasClass( "color" + i + "-tick" )){
			$(this).removeClass( "color" + i + "-tick" );		
	 		$(this).addClass( "color" + i );
		}
		i = i + 1;
	})
	
	// Ponemos el 'tick' de fondo al elemento
	var z = posicionDelLink( $(this).attr('title') )
	$(this).removeClass( "color" + z );
 	$(this).addClass( "color" + z + "-tick" );
		
	// Guardamos el valor en el campo del formulario
	var pnamespace = '<portlet:namespace/>';	
	$("#" + pnamespace + "color1").val( $(this).attr('title') );	
});


// Al cambiar el color de la paleta 2
$(".inlineList_2 li a.palette-item-inner").click(function() {
	
	// Quitamos la imagen de fondo 'tick' al elemento que la tuviera
	var i = 1;
	$(".inlineList_2 li a.palette-item-inner").each(function(index) {		
		if ( $( this ).hasClass( "color" + i + "-tick" )){
			$(this).removeClass( "color" + i + "-tick" );		
	 		$(this).addClass( "color" + i );
		}
		i = i + 1;
	})
	
	// Ponemos el 'tick' de fondo al elemento
	var z = posicionDelLink( $(this).attr('title') )
	$(this).removeClass( "color" + z );
 	$(this).addClass( "color" + z + "-tick" );
		
	// Guardamos el valor en el campo del formulario
	var pnamespace = '<portlet:namespace/>';	
	$("#" + pnamespace + "color2").val( $(this).attr('title') );	
});

function posicionDelLink( color ){
	
	if(color == "#9FC6E7"){
		return 1;		
	}
	if(color == "#5484ED"){
		return 2;		
	}
	if(color == "#A4BDFC"){
		return 3;		
	}
	if(color == "#51B749"){
		return 4;		
	}
	if(color == "#FBD75B"){
		return 5;		
	}
	if(color == "#FFB878"){
		return 6;		
	}
	if(color == "#FF887C"){
		return 7;		
	}
	if(color == "#DC2127"){
		return 8;		
	}
	if(color == "#DBADFF"){
		return 9;		
	}
	if(color == "#E1E1E1"){
		return 10;		
	}	
}

//Al cambiar una nueva imagen la mostramos en preview
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
</aui:script>