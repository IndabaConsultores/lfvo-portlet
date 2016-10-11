package net.indaba.lostandfound.portlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
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
import com.liferay.portal.kernel.servlet.SessionErrors;
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
	
	// HashMap con todos los parametros de todos los sites
	HashMap<String, HashMap<String, Object>> officesInfo = new HashMap<String, HashMap<String, Object>>();
	
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
		
		if( officesInfo.get(String.valueOf(officeId)) == null){
			
			try {
				Firebase firebase = new Firebase(ref.toString());
				FirebaseResponse response = firebase.get();
				HashMap<String, Object> office = (HashMap<String, Object>)response.getBody();
				
				officesInfo.put(String.valueOf(officeId), office);				
				// renderRequest.setAttribute("officeInfo", office);			
			} catch (FirebaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		renderRequest.setAttribute("officeInfo", officesInfo.get(String.valueOf(officeId)));		
		
		super.doView(renderRequest, renderResponse);
	}
	
	public void saveInfo(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException,
			PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		long officeId = themeDisplay.getScopeGroupId();
		HashMap<String, Object> infoUpdates = new HashMap<String, Object>();
		HashMap<String, Object> title = new HashMap<String, Object>();
		HashMap<String, Object> descr = new HashMap<String, Object>();
		HashMap<String, Object> location = new HashMap<String, Object>();

		// TITULO
		// 1) Castellano
		String title_cas = ParamUtil.get(actionRequest, "title_cas", "");
		if (!"".equals(title_cas)) {
			title.put("es", title_cas);
		}

		// 2) Euskera
		String title_eus = ParamUtil.get(actionRequest, "title_eus", "");
		if (!"".equals(title_eus)) {
			title.put("eu", title_eus);
		}

		if (!title.isEmpty()) {
			infoUpdates.put("title", title);
		}

		// Color 1
		String color1 = ParamUtil.get(actionRequest, "color1", "");
		if (!"".equals(color1)) {
			infoUpdates.put("color1", color1);
		}

		// Color 2
		String color2 = ParamUtil.get(actionRequest, "color2", "");
		if (!"".equals(color2)) {
			infoUpdates.put("color2", color2);
		}

		// Num Telefono
		String phoneNumber = ParamUtil.get(actionRequest, "phoneNumber", "");
		if (!"".equals(phoneNumber)) {
			infoUpdates.put("phoneNumber", phoneNumber);
		}

		// Email
		String emailAddress = ParamUtil.get(actionRequest, "emailAddress", "");
		if (!"".equals(emailAddress)) {
			infoUpdates.put("emailAddress", emailAddress);
		}

		// Icono
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
				actionRequest);
		File file = uploadRequest.getFile("itemImage");
		if (file == null || !file.exists()) {
			String icon = ParamUtil.get(actionRequest, "icon", "");
			if (!"".equals(icon)) {
				infoUpdates.put("icon", icon);
			}
		} else {
			String imageBase63String = Base64.encode(IOUtils.toByteArray(
					new FileInputStream(file)));
			imageBase63String = "data:image/jpeg;base64," + imageBase63String;
			infoUpdates.put("icon", imageBase63String);
		}

		// DESCRIPCION
		// 1) Castellano
		String description_cas = ParamUtil.get(actionRequest, "description_cas",
				"");
		if (!"".equals(description_cas)) {
			descr.put("es", description_cas);
		}

		// 2) Euskera
		String description_eus = ParamUtil.get(actionRequest, "description_eus",
				"");
		if (!"".equals(description_eus)) {
			descr.put("eu", description_eus);
		}

		if (!descr.isEmpty()) {
			infoUpdates.put("description", descr);
		}

		// URL
		String url = ParamUtil.get(actionRequest, "url", "");
		if (!"".equals(url)) {
			infoUpdates.put("url", url);
		}

		// POSICION DEL SITIO
		// 1) Latitud
		String latitude = ParamUtil.get(actionRequest, "latitude", "");
		if (!"".equals(latitude)) {
			location.put("latitude", latitude);
		}

		// 2) Longitud
		String longitude = ParamUtil.get(actionRequest, "longitude", "");
		if (!"".equals(longitude)) {
			location.put("longitude", longitude);
		}

		if (!location.isEmpty()) {
			infoUpdates.put("location", location);
		}

		DatabaseReference ref = FirebaseDatabase
				.getInstance()
				.getReference("/offices/" + officeId);
		ref.updateChildren(infoUpdates);

		officesInfo.put(String.valueOf(officeId), infoUpdates);
	}

	public void buildApp(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException,
			PortalException {
		ThemeDisplay td = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String officeId = String.valueOf(td.getSiteGroupId());
		String appName = ParamUtil.get(actionRequest, "appName", "LostAndFound" + officeId);
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);	
		
		File iconFile = uploadRequest.getFile("appIcon");
		File splashFile = uploadRequest.getFile("appSplash");
		String script = "/path/to/script.sh";
		String targetDir = "/path/to/target/dir/";

		BufferedImage icon = ImageIO.read(iconFile);
		BufferedImage splash = ImageIO.read(splashFile);
		
		boolean errors = false;
		
		if (appName.isEmpty()) {
			errors = true;
			SessionErrors.add(actionRequest, "app-name-error");
		}

		if (icon.getWidth() < 192 || icon.getHeight() < 192) {
			errors = true;
			SessionErrors.add(actionRequest, "icon-size-error");
		}
		
		if (splash.getWidth() < 2208 || splash.getHeight() < 2208) {
			errors = true;
			SessionErrors.add(actionRequest, "splash-size-error");
		}
		
		if (!errors) {
			File destIcon = new File(targetDir + "lfvoApp/resources/icon.png");
			File destSplash = new File(targetDir + "lfvoApp/resources/splash.png");
			
			if (destIcon.exists()) destIcon.delete();
			if (destSplash.exists()) destSplash.delete();
			
			iconFile.renameTo(destIcon);
			splashFile.renameTo(destSplash);
			
			Process p = new ProcessBuilder(script, officeId, appName)
					.directory(new File(targetDir))
					.redirectOutput(new File(targetDir + "/build" + officeId + ".log"))
					.inheritIO()
					.start();
			try {
				int processReturn = p.waitFor();
				if (processReturn != 0) {
					SessionErrors.add(actionRequest, "your-request-failed-to-complete");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				SessionErrors.add(actionRequest, "your-request-failed-to-complete");
			}
		}
	}
	
}
