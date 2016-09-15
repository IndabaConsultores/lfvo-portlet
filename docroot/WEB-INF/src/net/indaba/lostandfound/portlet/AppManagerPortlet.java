package net.indaba.lostandfound.portlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * Portlet implementation class AppManagerPortlet
 */
public class AppManagerPortlet extends MVCPortlet implements  ValueEventListener{
	
	HashMap<String, DatabaseReference> officeRefs = new HashMap<String, DatabaseReference>();
	HashMap<String, HashMap<String, Object>> officeInfo = new HashMap<String, HashMap<String, Object>>();
	
	@Override
	public void init() throws PortletException {
		
		if(FirebaseApp.getApps().size()<1){
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setDatabaseUrl("https://vivid-fire-8126.firebaseio.com/")
				    .setServiceAccount(AppManagerPortlet.class.getClassLoader().getResourceAsStream("firebase-service-account.json"))
				    .build();
			FirebaseApp.initializeApp(options);
		}
		
		DatabaseReference ref=null;
		List<Group> groups = GroupLocalServiceUtil.getGroups(-1, -1);
		
		for(Group group : groups){
			if(!officeRefs.containsKey(String.valueOf(group.getGroupId()))){
				ref = FirebaseDatabase
					    .getInstance()
					    .getReference("/offices/" + group.getGroupId());
				ref.addValueEventListener(this);
				officeRefs.put(String.valueOf(group.getGroupId()), ref);
			}
		}
		
		super.init();
	}
	
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		DatabaseReference ref=null;
		if(!officeRefs.containsKey(String.valueOf(themeDisplay.getScopeGroupId()))){
			ref = FirebaseDatabase
				    .getInstance()
				    .getReference("/offices/" + themeDisplay.getScopeGroupId());
			ref.addValueEventListener(this);
			officeRefs.put(String.valueOf(themeDisplay.getScopeGroupId()), ref);
		}
		
		renderRequest.setAttribute("officeInfo", officeInfo.get(String.valueOf(themeDisplay.getScopeGroupId())));
		
		super.doView(renderRequest, renderResponse);
	}
	
	public void saveInfo(ActionRequest actionRequest, ActionResponse actionResponse){
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		HashMap<String, Object> infoUpdates = new HashMap<String, Object>();
		
		String color1 = ParamUtil.get(actionRequest, "color1", ""); 
		if(!"".equals(color1)){
			infoUpdates.put("color1", color1);
		}
		
		String color2 = ParamUtil.get(actionRequest, "color2", ""); 
		if(!"".equals(color2)){
			infoUpdates.put("color2", color2);
		}
		
		DatabaseReference ref = officeRefs.get(String.valueOf(themeDisplay.getScopeGroupId()));
		if(ref!=null){
			ref.updateChildren(infoUpdates);
		}
		
	}

	@Override
	public void onDataChange(DataSnapshot arg0) {
		Object document = arg0.getValue();
        HashMap<String, Object> hm = (HashMap)document;
        
        for (String key : hm.keySet()) {
        	System.out.println(key + " --> " + hm.get(key));
			
		}
        String groupIdStr = hm.get("groupId").toString();
        if(groupIdStr!=null){
        	officeInfo.put(groupIdStr, hm);
        }
		/*OfficeInfo oi = (OfficeInfo)document; 
		System.out.println("kooooool " + oi.getId());*/
		
	}
	
	@Override
	public void onCancelled(DatabaseError arg0) {
		System.out.println("canceled");
		
	}
 

}
