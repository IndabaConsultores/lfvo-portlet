package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.util.portlet.PortletProps;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.impl.ItemImpl;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseSyncUtil {
	
	static private List<Item> liferayUnsyncedItems;
	static private List<Item> firebaseUnsyncedItems;
	
	static private long lastSyncDate = 0;

	static private final String FB_URI = "https://brilliant-torch-8285.firebaseio.com";
	

	public static boolean isSyncEnabled() {
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}

	private static List<Item> getLiferayItemsAfter(long liferayTS) {
		List<Item> items = new ArrayList<Item>();

		/*
		 * Get Liferay office items that were added/updated after last update
		 * time
		 */
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ItemImpl.class)
				.add(PropertyFactoryUtil.forName("modifiedDate").gt(new Date(liferayTS)));
		items.addAll(ItemLocalServiceUtil.dynamicQuery(query));
		return items;
	}

	private static List<Item> getFirebaseItemsAfter(long firebaseTS)
			throws FirebaseException, UnsupportedEncodingException {
		List<Item> items = new ArrayList<Item>();

		Firebase firebase = new Firebase(FB_URI + "/items");
		/* Get lost alerts */
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		FirebaseResponse response = firebase.get("/alert/lost");
		Map<String, Object> lostItems = response.getBody();
		Item item, itemLocal;
		for (Object o : lostItems.values()) {
			Map<String, Object> map = (Map<String, Object>) o;
			item = mapToItem(map);
			item.setType("lost");
			if (item.getItemId() != 0) {
				itemLocal = ItemLocalServiceUtil.fetchItem(item.getItemId());
				if (item.getModifiedDate().compareTo(itemLocal.getModifiedDate()) > 0)
					items.add(item);
			} else {
				item.setNew(true);
				items.add(item);
			}
		}
		/* Get found alerts */
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		response = firebase.get("/alert/found");
		lostItems = response.getBody();
		for (Object o : lostItems.values()) {
			Map<String, Object> map = (Map<String, Object>) o;
			item = mapToItem(map);
			item.setType("found");
			if (item.getItemId() != 0) {
				itemLocal = ItemLocalServiceUtil.fetchItem(item.getItemId());
				if (item.getModifiedDate().compareTo(itemLocal.getModifiedDate()) > 0)
					items.add(item);
			} else {
				item.setNew(true);
				items.add(item);
			}
		}
		return items;
	}

	public static void updateUnsyncedItems() throws FirebaseException, UnsupportedEncodingException {
		/* Get last update dates in Firebase */
		Firebase firebase = new Firebase(FB_URI + "/_TIMESTAMP");
		FirebaseResponse response = firebase.get("");

		Map<String, Object> responseMap = response.getBody();
		long liferayTS = (long) responseMap.get("Liferay");
		long nodejsTS = (long) responseMap.get("NodeJS");

		/* Get Liferay items that were added/updated after last update time */
		liferayUnsyncedItems = getLiferayItemsAfter(Math.max(liferayTS, nodejsTS));
		/* Get Firebase items that were added/updated after last update time */
		firebaseUnsyncedItems = getFirebaseItemsAfter(nodejsTS);
	}

	public static void updateUnsyncedItemsExh() throws UnsupportedEncodingException, FirebaseException {
		/* Get last exhaustive sync date in Firebase */
		Firebase firebase = new Firebase(FB_URI + "/_TIMESTAMP");
		FirebaseResponse response = firebase.get("");

		Map<String, Object> responseMap = response.getBody();
		long syncTS = (long) responseMap.get("Sync");

		/* Get Liferay items that were added/updated after last sync time */
		List<Item> lrItemList = getLiferayItemsAfter(syncTS);
		/* Get Firebase items that were added/updated after last sync time */
		List<Item> fbItemList = getFirebaseItemsAfter(syncTS);
		
		Map<Long, Item> lrItems = new HashMap<Long, Item>();
		List<Item> lrItemsList = new ArrayList<Item>();
		List<Item> fbItemsList = new ArrayList<Item>();
		
		/* Convert list to map for faster access by itemId */
		for (Item i : lrItemList) {
			lrItems.put(i.getItemId(), i);
		}
		Item lrItem;
		for (Item fbItem : fbItemList) {
			lrItem = lrItems.get(fbItem.getItemId());
			if (lrItem != null) {
				int dateComp = lrItem.getModifiedDate().compareTo(fbItem.getModifiedDate());
				if (dateComp == 0) {
					/* both items are in sync; remove from LRList */
					lrItems.remove(lrItem.getItemId());
				} else if (dateComp < 0) {
					/* fbItem is more recent; add to fbList */
					fbItemsList.add(fbItem);
				}
			} else {
				/* Item exists in FB but not in LR */
				fbItemsList.add(fbItem);
			}
		}
		liferayUnsyncedItems = new ArrayList<Item>(lrItems.values());
		firebaseUnsyncedItems = fbItemList;
		lastSyncDate = System.currentTimeMillis();
	}
	
	public static List<Item> getLiferayUnsyncedItems() {
		return liferayUnsyncedItems;
	}
	
	public static List<Item> getFirebaseUnsyncedItems() {
		return firebaseUnsyncedItems;
	}
	
	public static void resyncItems() throws UnsupportedEncodingException, 
	FirebaseException, JacksonUtilityException, PortalException {
		/* Push items to Firebase */
		for (Item i : liferayUnsyncedItems) {
			addOrUpdateItem(i);
		}
		liferayUnsyncedItems = null;

		/* Add items to Database */
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setUserId(25602);
		for (Item i : firebaseUnsyncedItems) {
			ItemLocalServiceUtil.addOrUpdateItem(i, serviceContext);
		}			
		firebaseUnsyncedItems = null;
		Map<String, Object> dateMap = new HashMap<String, Object>();
		dateMap.put("Liferay", System.currentTimeMillis());
		dateMap.put("NodeJS", System.currentTimeMillis());
		if (lastSyncDate != 0)
			dateMap.put("Sync", lastSyncDate);
		updateModifiedAt(dateMap);
	}
	
	private static void updateModifiedAt(Date date)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Map<String, Object> dateMap = new HashMap<String, Object>();
		dateMap.put("Liferay", date);
		updateModifiedAt(dateMap);
	}
	
	private static void updateModifiedAt(Map<String, Object> dateMap)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);
		firebase.patch("/_TIMESTAMP", dateMap);
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
				updateModifiedAt(item.getModifiedDate());
				_log.debug("Firebase update sucessful");
			} else {
				_log.debug("Firebase update unsuccessful. Response code: " + response.getCode());
			}
		} else { /* Item does not exist in Firebase: create */
			response = firebase.post(itemMap);
			if (response.getCode() == 200) {
				updateModifiedAt(item.getModifiedDate());
				_log.debug("Firebase create sucessful");
			} else {
				_log.debug("Firebase create unsuccessful. Response code: " + response.getCode());
			}
		}
	}

	public static void deleteItem(Item item)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		String itemTypePath = getItemPath(item);

		Firebase firebase = new Firebase(FB_URI + itemTypePath);

		String itemKey = getFirebaseKey(item);
		FirebaseResponse response;
		if (itemKey != null) {
			response = firebase.delete("/" + itemKey);
			if (response.getCode() == 200) {
				_log.debug("Firebase delete sucessful");
				updateModifiedAt(item.getModifiedDate());
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
		itemMap.put("modifiedAt", item.getModifiedDate());
		itemMap.put("groupId", item.getGroupId());
		itemMap.put("companyId", item.getCompanyId());
		Map<String, Object> location = new HashMap<String, Object>();
		location.put("latitude", item.getLat());
		location.put("longitude", item.getLng());
		itemMap.put("location", location);
		itemMap.put("liferay", true);
		return itemMap;
	}

	private static Item mapToItem(Map<String, Object> map) {
		Item item = new ItemImpl();
		item.setItemId((int) map.get("id"));
		item.setName((String) map.get("name"));
		item.setDescription((String) map.get("description"));
		item.setCreateDate(new Date((long) map.get("createdAt")));
		item.setModifiedDate(new Date((long) map.get("modifiedAt")));
		item.setGroupId((int) map.get("groupId"));
		item.setCompanyId((int) map.get("companyId"));
		Map<String, Object> locationMap = (Map<String, Object>) map.get("location");
		item.setLat((int) locationMap.get("latitude"));
		item.setLng((int) locationMap.get("longitude"));
		return item;
	}

	private final static Log _log = LogFactoryUtil.getLog(FirebaseSyncUtil.class);

}
