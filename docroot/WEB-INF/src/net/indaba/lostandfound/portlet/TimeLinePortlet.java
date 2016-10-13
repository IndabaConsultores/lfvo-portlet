package net.indaba.lostandfound.portlet;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.io.IOUtils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.model.TimeLinePopUp;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.service.LFImageLocalServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 * Portlet implementation class TimeLinePortlet
 */
public class TimeLinePortlet extends MVCPortlet {	
	
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
	
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		HashMap<Date, ArrayList<HashMap<String, Object>>> resultado = new HashMap<Date, ArrayList<HashMap<String, Object>>>();
		List<TimeLinePopUp> listaPopUps = new ArrayList<TimeLinePopUp>();
				
		// *********************
		// 1) Sites de LIFERAY
		// *********************
		List<Group> offices = new ArrayList<Group>();		
		String mainSiteGroupId = com.liferay.util.portlet.PortletProps.get("lfvo.main.site.group.id");		
		if(mainSiteGroupId != null && !"".equals(mainSiteGroupId)){
			try {		
				offices = GroupLocalServiceUtil.getGroups(themeDisplay.getCompanyId(), Long.parseLong(mainSiteGroupId), true);				
				
			} catch (NumberFormatException e) {			
				e.printStackTrace();
				offices = GroupLocalServiceUtil.getGroups(themeDisplay.getCompanyId(), 0, true);				
				
			}			
		} else {
			offices = GroupLocalServiceUtil.getGroups(themeDisplay.getCompanyId(), 0, true);			
		}
		
		// *********************
		// 2) Sites de FIREBASE
		// *********************
		HashMap<String, Object> officesMap = new HashMap<String, Object>();
		try {
			DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/offices/");	
			Firebase firebase = new Firebase(ref.toString());
			FirebaseResponse response = firebase.get();
			officesMap = (HashMap<String, Object>)response.getBody();	
		
		} catch (FirebaseException e2) {
			e2.printStackTrace();
		}		
		
		if( officesMap != null){		
			for(Group office : offices){				
				
				HashMap<String, Object> officeBD = (HashMap<String, Object>) officesMap.get(String.valueOf(office.getGroupId())); // Site Firebase <> Site Liferay
				if(officeBD!=null){

					try {				
						List<Item> items = ItemLocalServiceUtil.getItems(office.getGroupId(), -1, -1);
						for(Item item : items){
							
							HashMap<String, Object> miItem = new HashMap<String, Object>();
														
							// Creamos una fecha con formato simple
							SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
							String fechaFormat = dt1.format(item.getCreateDate());
							SimpleDateFormat dt2 = new SimpleDateFormat("yyyy/MM/dd");
							String fechaFormatEus = dt2.format(item.getCreateDate());
														
							// Quitamos la hora y minutos de la fecha original
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							Date b = new Date();
							try {
								b = formatter.parse(fechaFormat);
							} catch (ParseException e) {
								
							}
							
							// PopUp - Mapa Street
							TimeLinePopUp miPopUp = new TimeLinePopUp();
							miPopUp.setDate_es(fechaFormat);
							miPopUp.setDate_eu(fechaFormatEus);
							miPopUp.setName(item.getName());
							miPopUp.setImage(this.obtenerImagenItem(item.getItemId()));
							miPopUp.setLat(String.valueOf(item.getLat()));
							miPopUp.setLng(String.valueOf(item.getLng()));
							
							listaPopUps.add(miPopUp);
							
							// HashMaps - Timeline
							miItem.put("image", this.obtenerImagenItem(item.getItemId()));							
							miItem.put("name", item.getName());
							miItem.put("desc", item.getDescription());
							
							if (resultado.get(b) == null) {
								resultado.put(b, new ArrayList<HashMap<String, Object>>());
							}
							resultado.get(b).add(miItem);
						}
					} catch (PortalException e) {				
						e.printStackTrace();
					}					
				}
			}
		}		
		
		// Lista de PopUps
		Type listType = new TypeToken<List<TimeLinePopUp>>() {}.getType();
		Gson gson = new Gson();
		String json = gson.toJson(listaPopUps, listType);
		
		renderRequest.setAttribute("popUps", json);
				
		// En vez de ordenar el hashmap, ordenamos las keys
		ArrayList<Date> miArray = new ArrayList<Date>();		
		for (Date date : resultado.keySet()) {
			miArray.add(date);
		}
		Collections.sort(miArray);
		
		renderRequest.setAttribute("resultado", resultado);	
		renderRequest.setAttribute("orderedKeys", miArray);	
		
		super.doView(renderRequest, renderResponse);
	}
	
	private String obtenerImagenItem(long itemId){
		
		String image = "";
		
		List<LFImage> lfImages = LFImageLocalServiceUtil.findByItemId(itemId);
		for(LFImage lfImage : lfImages){
			StringWriter writer = new StringWriter();
			writer.append("data:image/gif;base64,");
			try {
				IOUtils.copy(lfImage.getImage().getBinaryStream(), writer);
				image = writer.toString();
			} catch (IOException | SQLException e) {
				
			}			
			break;
		}	
		
		return image;
	}
}