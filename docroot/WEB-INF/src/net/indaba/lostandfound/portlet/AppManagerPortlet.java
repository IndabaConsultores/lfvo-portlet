package net.indaba.lostandfound.portlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.io.IOUtils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 * Portlet implementation class AppManagerPortlet
 */
public class AppManagerPortlet extends MVCPortlet{
		
	@Override
	public void init() throws PortletException {
		
		if(FirebaseApp.getApps().size()<1){
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setDatabaseUrl("https://lfvo-test.firebaseio.com/")
				    .setServiceAccount(AppManagerPortlet.class.getClassLoader().getResourceAsStream("firebase-service-account.json"))
				    .build();
			FirebaseApp.initializeApp(options);
		}
				
		super.init();
	}
	
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long officeId = themeDisplay.getScopeGroupId();
		DatabaseReference ref=null;
		ref = FirebaseDatabase
			    .getInstance()
			    .getReference("/offices/" + officeId);
		try {
			Firebase firebase = new Firebase(ref.toString());
			FirebaseResponse response = firebase.get();
			HashMap<String, Object> office = (HashMap<String, Object>)response.getBody();
			renderRequest.setAttribute("officeInfo", office);
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.doView(renderRequest, renderResponse);
	}
	
	public void saveInfo(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException, PortalException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long officeId = themeDisplay.getScopeGroupId();
		HashMap<String, Object> infoUpdates = new HashMap<String, Object>();
				
		// Color 1
		String color1 = ParamUtil.get(actionRequest, "color1", ""); 
		if(!"".equals(color1)){
			infoUpdates.put("color1", color1);
		}
				
		// Color 2
		String color2 = ParamUtil.get(actionRequest, "color2", ""); 
		if(!"".equals(color2)){
			infoUpdates.put("color2", color2);
		}
		
		// Num Telefono
		String phoneNumber = ParamUtil.get(actionRequest, "phoneNumber", ""); 
		if(!"".equals(phoneNumber)){
			infoUpdates.put("phoneNumber", phoneNumber);
		}
		
		// Email
		String emailAddress = ParamUtil.get(actionRequest, "emailAddress", ""); 
		if(!"".equals(emailAddress)){
			infoUpdates.put("emailAddress", emailAddress);
		}
		
		// Icono		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);		
		File file = uploadRequest.getFile("itemImage");
		if (file == null || !file.exists()){			
			String icon = ParamUtil.get(actionRequest, "icon", "");
			if(!"".equals(icon)){
				infoUpdates.put("icon", icon);
			}		
		}else{
			String imageBase63String = Base64.encode(IOUtils.toByteArray(new FileInputStream(file)));
			imageBase63String = "data:image/jpeg;base64," + imageBase63String;			
			infoUpdates.put("icon", imageBase63String);
		}
		
		// Descripcion
		String description = ParamUtil.get(actionRequest, "description", ""); 
		if(!"".equals(description)){
			infoUpdates.put("description", description);
		}
		
		DatabaseReference ref = FirebaseDatabase
			    .getInstance()
			    .getReference("/offices/" + officeId);
		ref.updateChildren(infoUpdates);
		
	}
	
}
