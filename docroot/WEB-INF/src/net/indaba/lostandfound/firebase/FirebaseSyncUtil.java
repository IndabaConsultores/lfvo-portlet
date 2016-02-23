package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.liferay.util.portlet.PortletProps;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import net.indaba.lostandfound.model.Item;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseSyncUtil {

	static private final String FB_URI = "https://brilliant-torch-8285.firebaseio.com";

	public static boolean isSyncEnabled(){
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}
	
	public static void addOrUpdateItem(Item item)
			throws FirebaseException, JacksonUtilityException, UnsupportedEncodingException {
		String itemTypePath = getItemPath(item);

		Firebase firebase = new Firebase(FB_URI + itemTypePath);

		String itemKey = getFirebaseKey(item);
		Map<String, Object> itemMap = itemToMap(item);
		FirebaseResponse response;
		if (itemKey != null) { /* Item exists already in Firebase: update */
			response = firebase.patch("/" + itemKey, itemMap);
			if (response.getCode() == 200) {
				_log.debug("Firebase update sucessful");
			} else {
				_log.debug("Firebase update unsuccessful. Response code: " + response.getCode());
			}
		} else {               /* Item does not exist in Firebase: create */
			response = firebase.post(itemMap);
			if (response.getCode() == 200) {
				_log.debug("Firebase create sucessful");
			} else {
				_log.debug("Firebase create unsuccessful. Response code: " + response.getCode());
			}
		}
	}

	public static void deleteItem(Item item) throws FirebaseException, UnsupportedEncodingException {
		String itemTypePath = getItemPath(item);

		Firebase firebase = new Firebase(FB_URI + itemTypePath);
		
		String itemKey = getFirebaseKey(item);
		FirebaseResponse response;
		if (itemKey != null) {
			response = firebase.delete("/" + itemKey);
			if (response.getCode() == 200) {
				_log.debug("Firebase delete sucessful");
			} else {
				_log.debug("Firebase delete unsuccessful. Response code: " + response.getCode());
			}
		} else {
			_log.debug("Could not find item with id " + item.getItemId());
		}
	}

	/**
	 * Gets the Firebase object key for the pertinent item.
	 * 
	 * @param item
	 * @return Firebase auto-generated key for the item with id = item.itemId
	 * @throws FirebaseException
	 * @throws UnsupportedEncodingException
	 */
	private static String getFirebaseKey(Item item) throws FirebaseException, UnsupportedEncodingException {
		String itemTypePath = getItemPath(item);

		Firebase firebase = new Firebase(FB_URI + itemTypePath);
		
		firebase.addQuery("orderBy", "\"id\"");
		firebase.addQuery("equalTo", String.valueOf(item.getItemId()));
		FirebaseResponse response = firebase.get();
		if (response.getCode() == 200) {
			Map<String, Object> responseMap = response.getBody();
			Object[] keys = responseMap.keySet().toArray();
			if (keys.length > 0) {
				return (String) keys[0];
			} else {
				return null;
			}
		} else {
			_log.debug("Firebase get key unsuccessfull. Response code: " + response.getCode());
		}
		return null;
	}

	private static String getItemPath(Item item) {
		String itemTypePath = "/items";

		switch (item.getType()) {
		case "lost":
			itemTypePath += "/alert/lost";
			break;
		case "found":
			itemTypePath += "/alert/found";
			break;
		default:
			itemTypePath += "/office";
		}
		return itemTypePath;
	}

	private static Map<String, Object> itemToMap(Item item) {
		HashMap<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("id", item.getItemId());
		itemMap.put("name", item.getName());
		itemMap.put("description", item.getDescription());
		itemMap.put("createdAt", item.getCreateDate());
		itemMap.put("groupId", item.getGroupId());
		itemMap.put("companyId", item.getCompanyId());
		Map<String, Object> location = new HashMap<String, Object>();
		location.put("latitude", item.getLat());
		location.put("longitude", item.getLng());
		itemMap.put("location", location);
		itemMap.put("liferay", true);
		return itemMap;
	}

	private final static Log _log = LogFactoryUtil.getLog(FirebaseSyncUtil.class);

}
