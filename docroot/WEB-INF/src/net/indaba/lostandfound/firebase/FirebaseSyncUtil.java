package net.indaba.lostandfound.firebase;

import java.util.HashMap;
import java.util.Map;

import net.indaba.lostandfound.model.Item;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseSyncUtil {

	static private final String FB_URI = "https://brilliant-torch-8285.firebaseio.com";
	
	public static void addOrUpdateItem(Item item) throws Exception, FirebaseException, JacksonUtilityException{
		Firebase firebase = new Firebase(FB_URI + "/items/office");
		Map<String, Object> itemMap = itemToMap(item);
		FirebaseResponse response = firebase.post(itemMap);
	}
	
	public static void removeItem(Item item) throws Exception, FirebaseException, JacksonUtilityException{
		Firebase firebase = new Firebase(FB_URI + "/items/office");
		firebase.addQuery("orderBy", "\"id\"");
		firebase.addQuery("equalTo", String.valueOf(item.getItemId()));
		FirebaseResponse response = firebase.get();
	}
	
	private static Map<String, Object> itemToMap(Item item) {
		HashMap<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("id", item.getItemId());
		itemMap.put("name", item.getName());
		itemMap.put("description", item.getDescription());
		return itemMap;
	}
	
}
