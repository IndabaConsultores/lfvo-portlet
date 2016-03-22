package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.util.portlet.PortletProps;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.impl.ItemImpl;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseItemSyncUtil {
	
	static private FirebaseItemSyncUtil instance;

	private final String FB_BASE_URI = "https://brilliant-torch-8285.firebaseio.com";
	private final String FB_URI = FB_BASE_URI + "/items";
	
	private List<Item> liferayUnsyncedItems;
	private Map<String, Item> firebaseUnsyncedItems;

	private long lastSyncDate = 0;
	
	private FirebaseItemSyncUtil() {
		super();
	}
	
	public static FirebaseItemSyncUtil getInstance() {
		if (instance == null) {
			instance = new FirebaseItemSyncUtil();
		}
		return instance;
	}

	public boolean isSyncEnabled() {
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}

	private List<Item> getLiferayItemsAfter(long liferayTS) {
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

	private Map<String, Item> getFirebaseItemsAfter(long firebaseTS)
			throws FirebaseException, UnsupportedEncodingException {
		Map<String, Item> items = new LinkedHashMap<String, Item>();

		Firebase firebase = new Firebase(FB_URI);
		/* Get lost alerts */
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		FirebaseResponse response = firebase.get("/alert/lost");
		Map<String, Object> lostItems = response.getBody();
		Item item, itemLocal;
		Iterator<Entry<String, Object>> it = lostItems.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			Map<String, Object> map = (Map<String, Object>) e.getValue();
			item = mapToItem(map);
			item.setType("lost");
			if (item.getItemId() != 0) {
				items.put(e.getKey(), item);
			} else {
				item.setNew(true);
				items.put(e.getKey(), item);
			}
		}
		/* Get found alerts */
		firebase = new Firebase(FB_URI);
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		response = firebase.get("/alert/found");
		lostItems = response.getBody();
		it = lostItems.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			Map<String, Object> map = (Map<String, Object>) e.getValue();
			item = mapToItem(map);
			item.setType("found");
			if (item.getItemId() != 0) {
				items.put(e.getKey(), item);
			} else {
				item.setNew(true);
				items.put(e.getKey(), item);
			}
		}
		/* Get office items */
		firebase = new Firebase(FB_URI);
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		response = firebase.get("/office");
		lostItems = response.getBody();
		it = lostItems.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			Map<String, Object> map = (Map<String, Object>) e.getValue();
			item = mapToItem(map);
			item.setType("office");
			if (item.getItemId() != 0) {
				items.put(e.getKey(), item);
			} else {
				item.setNew(true);
				items.put(e.getKey(), item);
			}
		}
		return items;
	}

	public void updateUnsyncedItems() throws FirebaseException, UnsupportedEncodingException {
		/* Get last update dates in Firebase */
		Firebase firebase = new Firebase(FB_BASE_URI + "/_TIMESTAMP");
		FirebaseResponse response = firebase.get("");

		Map<String, Object> responseMap = response.getBody();
		long liferayTS = (long) responseMap.get("Liferay");
		long nodejsTS = (long) responseMap.get("NodeJS");

		/* Get Liferay items that were added/updated after last update time */
		liferayUnsyncedItems = getLiferayItemsAfter(Math.max(liferayTS, nodejsTS));
		/* Get Firebase items that were added/updated after last update time */
		firebaseUnsyncedItems = getFirebaseItemsAfter(nodejsTS);
	}

	public void updateUnsyncedItemsExh() throws UnsupportedEncodingException, FirebaseException {
		/* Get last exhaustive sync date in Firebase */
		Firebase firebase = new Firebase(FB_BASE_URI + "/_TIMESTAMP");
		FirebaseResponse response = firebase.get("");

		Map<String, Object> responseMap = response.getBody();
		Object o = responseMap.get("Sync");
		long syncTS = (o == null) ? 0 : new Long(o.toString());

		/* Get Liferay items that were added/updated after last sync time */
		List<Item> lrItemList = getLiferayItemsAfter(syncTS);
		/* Get Firebase items that were added/updated after last sync time */
		Map<String, Item> fbItemsAux = getFirebaseItemsAfter(syncTS);
		Map<String, Item> fbItems = new LinkedHashMap<String, Item>();

		Map<Long, Item> lrItems = new HashMap<Long, Item>();

		/* Convert list to map for faster access by itemId */
		for (Item i : lrItemList) {
			lrItems.put(i.getItemId(), i);
		}
		Item lrItem, fbItem;
		for (Entry<String, Item> e : fbItemsAux.entrySet()) {
			fbItem = e.getValue();
			lrItem = lrItems.get(fbItem.getItemId());
			if (lrItem != null) {
				int dateComp = lrItem.getModifiedDate().compareTo(fbItem.getModifiedDate());
				if (dateComp == 0) {
					/* both items are in sync; remove from both*/
					lrItems.remove(lrItem.getItemId());
				} else if (dateComp > 0) {
					/* lrItem is more recent; remove from fbItems */
				} else {
					/* fbItem is more recent; remove from lrItems*/
					lrItems.remove(lrItem.getItemId());
					fbItems.put(e.getKey(), e.getValue());
				}
			} else {
				/* Item exists in FB but not in LR */
				fbItems.put(e.getKey(), e.getValue());
			}
		}
		liferayUnsyncedItems = new ArrayList<Item>(lrItems.values());
		firebaseUnsyncedItems = fbItems;
		lastSyncDate = System.currentTimeMillis();
	}

	public List<Item> getLiferayUnsyncedItems() {
		return liferayUnsyncedItems;
	}

	public List<Item> getFirebaseUnsyncedItems() {
		return new ArrayList<Item>(firebaseUnsyncedItems.values());
	}

	public void resyncItems()
			throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException, PortalException {
		/* Push items to Firebase */
		for (Item i : liferayUnsyncedItems) {
			addOrUpdateItem(i);
		}
		liferayUnsyncedItems = null;

		/* Add items to Database */
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setUserId(25602);
		Item item;
		boolean isNew;
		for (Entry<String, Item> e : firebaseUnsyncedItems.entrySet()) {
			item = e.getValue();
			isNew = e.getValue().isNew();
			item = ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext);
			if (isNew) {
				String itemTypePath = getItemPath(item);

				Firebase firebase = new Firebase(FB_URI + itemTypePath);
				String itemKey = e.getKey();
				Map<String, Object> itemMap = new HashMap<String, Object>();
				itemMap.put("id", item.getItemId());
				FirebaseResponse response;
				response = firebase.patch("/" + itemKey, itemMap);
				if (response.getCode() == 200) {
					updateModifiedAt(item.getModifiedDate());
					_log.debug("Firebase update sucessful");
				} else {
					_log.debug("Firebase update unsuccessful. Response code: " + response.getCode());
				}
			}
		}
		firebaseUnsyncedItems = null;
		Map<String, Object> dateMap = new HashMap<String, Object>();
		dateMap.put("Liferay", System.currentTimeMillis());
		dateMap.put("NodeJS", System.currentTimeMillis());
		if (lastSyncDate != 0)
			dateMap.put("Sync", lastSyncDate);
		updateModifiedAt(dateMap);
	}

	private void updateModifiedAt(Date date)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Map<String, Object> dateMap = new HashMap<String, Object>();
		dateMap.put("Liferay", date);
		updateModifiedAt(dateMap);
	}

	private void updateModifiedAt(Map<String, Object> dateMap)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_BASE_URI);
		firebase.patch("/_TIMESTAMP", dateMap);
	}

	public void addOrUpdateItem(Item item)
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

	public void deleteItem(Item item)
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
	public String getFirebaseKey(Item item) throws FirebaseException, UnsupportedEncodingException {
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

	public String getItemPath(Item item) {
		String itemTypePath;

		switch (item.getType()) {
		case "lost":
			itemTypePath = "/alert/lost";
			break;
		case "found":
			itemTypePath = "/alert/found";
			break;
		default:
			itemTypePath = "/office";
		}
		return itemTypePath;
	}

	private Map<String, Object> itemToMap(Item item) {
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
		//Map<Long, Boolean> categories = new HashMap<Long, Boolean>();
		itemMap.put("liferay", true);
		return itemMap;
	}

	private Item mapToItem(Map<String, Object> map) {
		Item item = new ItemImpl();
		Object o;
		o = map.get("id");
		item.setItemId(o != null ? Long.valueOf(o.toString()) : 0);
		o = map.get("name");
		item.setName(o != null ? (String) o : "");
		o = map.get("description");
		item.setDescription(o != null ? (String) o : "");
		o = map.get("createdAt");
		item.setCreateDate(new Date(o != null ? Long.valueOf(o.toString()) : 0));
		o = map.get("modifiedAt");
		item.setModifiedDate(new Date(o != null ? Long.valueOf(o.toString()) : 0));
		o = map.get("groupId");
		item.setGroupId(o != null ? Long.valueOf(o.toString()) : 0);
		o = map.get("companyId");
		item.setCompanyId(o != null ? Long.valueOf(o.toString()) : 0);
		o = map.get("location");
		if (o != null) {
			Map<String, Object> locationMap = (Map<String, Object>) o;
			o = locationMap.get("latitude");
			item.setLat(o != null ? Long.valueOf(o.toString()) : 0);
			o = locationMap.get("longitude");
			item.setLng(o != null ? Long.valueOf(o.toString()) : 0);
		}
		return item;
	}

	private final Log _log = LogFactoryUtil.getLog(FirebaseItemSyncUtil.class);

}
