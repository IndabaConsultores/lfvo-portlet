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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

	private final String FB_BASE_URI = PortletProps.get("firebase.url");
	private final String FB_URI = PortletProps.get("firebase.url") + "/items";
	private final String FB_Cat_URI = FB_BASE_URI + "/categories";

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

	public Future<String> add(Item item, Future<Object> wait) {
		if (wait != null) {
			// Wait for future f to finish
			try {
				wait.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(() -> {
				String itemTypePath = getItemPath(item);
				Firebase firebase;
				try {
					firebase = new Firebase(FB_URI + itemTypePath);
					Map<String, Object> itemMap = itemToMap(item);
					FirebaseResponse response = firebase.post(itemMap);
					if (response.getCode() == 200) {
						_log.debug("Firebase create sucessful");
						return (String) response.getBody().get("name");
					} else {
						_log.debug("Firebase create unsuccessful. Response code: " + response.getCode());
						return null;
					}
				} catch (FirebaseException | JacksonUtilityException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
		});
		executor.shutdown();
		return future;
	}

	public Future<String> update(Item item, Future<Object> wait) {
		if (wait != null) {
			// Wait for future f to finish
			try {
				wait.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(() -> {
			try {
				String firebaseKey = getFirebaseKey(item);
				return update(item, firebaseKey, null).get();
			} catch (FirebaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;		
		});
		return future;
	}

	public Future<String> update(Item item, String firebaseKey, Future<Object> wait) {
		if (wait != null) {
			// Wait for future f to finish
			try {
				wait.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(() -> {
				String itemTypePath = getItemPath(item);
				try {
					Firebase firebase = new Firebase(FB_URI + itemTypePath);
					Map<String, Object> itemMap = itemToMap(item);
					FirebaseResponse response;
					response = firebase.patch("/" + firebaseKey, itemMap);
					if (response.getCode() == 200) {
						_log.debug("Firebase update sucessful");
						return (String) response.getBody().get("name");
					} else {
						_log.debug("Firebase update unsuccessful. Response code: " + response.getCode());
					}
				} catch (FirebaseException | JacksonUtilityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
		});
		executor.shutdown();
		return future;
	}

	public Future<String> addOrUpdateItem(Item item, Future<Object> wait) {
		if (wait != null) {
			// Wait for future f to finish
			try {
				wait.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(() -> {
			try {
				String itemKey = getFirebaseKey(item);
				if (itemKey != null) { /* Item exists already in Firebase: update */
					return update(item, itemKey, null).get();
				} else { /* Item does not exist in Firebase: create */
					return add(item, null).get();
				}
			} catch (FirebaseException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		});
		executor.shutdown();
		return future;
	}

	public Future<Object> deleteItem(Item item, Future<Object> wait) {
		if (wait != null) {
			// Wait for future f to finish
			try {
				wait.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Object> future = executor.submit(() -> {
				String itemTypePath = getItemPath(item);
				try {
					Firebase firebase = new Firebase(FB_URI + itemTypePath);
					String itemKey = getFirebaseKey(item);
					FirebaseResponse response;
					if (itemKey != null) {
						addRelations(item, new ArrayList<AssetCategory>(), null).get();
						response = firebase.delete("/" + itemKey);
						if (response.getCode() == 200) {
							_log.debug("Firebase delete sucessful");
							return true;
						} else {
							_log.debug("Firebase delete unsuccessful. Response code: " + response.getCode());
						}
					} else {
						_log.debug("Could not find item with id " + item.getItemId());
					}
				} catch (FirebaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
		});
		executor.shutdown();
		return future;
	}

	public Future<Object> addRelations(Item item, List<AssetCategory> acs, Future<String> wait) {
		if (wait != null) {
			// Wait for future f to finish
			try {
				wait.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		return executor.submit(() -> {
				String itemTypePath = getItemPath(item);
				String itemKey;
				try {
					itemKey = getFirebaseKey(item);
					if (itemKey != null) {
						Firebase firebase;
						FirebaseResponse response;

						/* Obtain previous categories */
						firebase = new Firebase(FB_URI);
						response = firebase.get(itemTypePath + "/" + itemKey);
						/* oldCatsMap will gather the categories to be removed */
						String oldCat;
						Map<String, Boolean> oldCatsMap = new HashMap<String, Boolean>();
						if (response.getCode() == 200) {
							Object o = response.getBody().get("category");
							if (o != null) {
								oldCat = o.toString();
								oldCatsMap.put(oldCat, true);
							}
						}

						/* Update "item" field on categories */
						Map<Long, Boolean> catKeysMap = new HashMap<Long, Boolean>();
						for (AssetCategory ac : acs) {
							/* Compare previous categories with current categories */
							long cid = ac.getCategoryId();
							if (oldCatsMap.containsKey(String.valueOf(cid))) {
								/* Item has the category; don't remove */
								oldCatsMap.remove(String.valueOf(cid));
							} else {
								/* New category */
								Map<String, Object> categoryMap = new HashMap<String, Object>();
								categoryMap.put(itemKey, true);
								firebase = new Firebase(FB_Cat_URI);
								response = firebase.patch("/" + ac.getCategoryId() + "/items", categoryMap);
								if (response.getCode() == 200) {
									_log.debug("Firebase category add sucessful");
								} else {
									_log.debug("Firebase category add unsuccessful. Response code: " + response.getCode());
								}
							}
							/* Collect the categoryId into the map */
							catKeysMap.put(ac.getCategoryId(), true);
						}

						/* Delete item reference from remaining old categories */
						for (Entry<String, Boolean> e : oldCatsMap.entrySet()) {
							firebase = new Firebase(FB_Cat_URI);
							response = firebase.delete("/" + e.getKey() + "/items/" + itemKey);
						}

						/* Update item's "categories" field */
						Map<String, Object> itemMap = new HashMap<String, Object>();
						itemMap.put("liferay", true);
						if (acs.size() > 0) {
							long catId = acs.get(0).getCategoryId();
							itemMap.put("category", catId);
						} else {
							itemMap.put("category", null);
						}

						firebase = new Firebase(FB_URI + itemTypePath);
						response = firebase.patch(itemKey, itemMap);
						if (response.getCode() == 200) {
							_log.debug("Firebase category add sucessful");
						} else {
							_log.debug("Firebase category add unsuccessful. Response code: " + response.getCode());
						}
					}
				} catch (FirebaseException | JacksonUtilityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return null;
		});
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
		case "alert":
		case "lost":
		case "found":
			itemTypePath = "/alert";
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
		itemMap.put("office", item.getGroupId());
		itemMap.put("companyId", item.getCompanyId());
		itemMap.put("type", item.getType());
		Map<String, Object> location = new HashMap<String, Object>();
		location.put("latitude", item.getLat());
		location.put("longitude", item.getLng());
		itemMap.put("location", location);
		// Map<Long, Boolean> categories = new HashMap<Long, Boolean>();
		itemMap.put("createdBy", PortletProps.get("firebase.user.id"));
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
		o = map.get("office");
		item.setGroupId(o != null ? Long.valueOf(o.toString()) : 0);
		o = map.get("companyId");
		item.setCompanyId(o != null ? Long.valueOf(o.toString()) : 0);
		o = map.get("type");
		item.setType(o != null ? String.valueOf(o) : "office"); 
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

	private List<Item> getLiferayItemsAfter(long liferayTS) {
		/* Get Liferay items that were added/updated after liferayTS */
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ItemImpl.class)
				.add(PropertyFactoryUtil.forName("modifiedDate").gt(new Date(liferayTS)));
		return ItemLocalServiceUtil.dynamicQuery(query);
	}

	private Map<String, Item> getFirebaseItemsAfter(long firebaseTS)
			throws FirebaseException, UnsupportedEncodingException {
		Map<String, Item> items = new LinkedHashMap<String, Item>();

		Firebase firebase = new Firebase(FB_URI);
		/* Get alerts */
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		FirebaseResponse response = firebase.get("/alert");
		Map<String, Object> lostItems = response.getBody();
		Item item;
		Iterator<Entry<String, Object>> it = lostItems.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			Map<String, Object> map = (Map<String, Object>) e.getValue();
			item = mapToItem(map);
			if (item.getItemId() != 0) {
				items.put(e.getKey(), item);
			} else {
				item.setNew(true);
				items.put(e.getKey(), item);
			}
		}
		
		/* Get office items */
		//TODO is this necessary?
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

	public Map<Item, String> getUnsyncedItemsSince(long date) throws UnsupportedEncodingException, FirebaseException {
		Map<Item, String> unsyncedItems = new HashMap<Item, String>();

		/* Get Liferay items that were added/updated after last sync time */
		List<Item> lrItemList = getLiferayItemsAfter(date);
		/* Get Firebase items that were added/updated after last sync time */
		Map<String, Item> fbItemSet = getFirebaseItemsAfter(date);

		Map<Long, Item> lrItemSet = new HashMap<Long, Item>();

		/* Convert list to map for easier access by itemId */
		for (Item i : lrItemList) {
			lrItemSet.put(i.getItemId(), i);
		}
		/* Add fbItem in fbItemSet */
		Item lrItem, fbItem;
		for (Entry<String, Item> e : fbItemSet.entrySet()) {
			fbItem = e.getValue();
			lrItem = lrItemSet.get(fbItem.getItemId());
			if (lrItem != null) {
				/* item exists in FB and LR; compare modified date */
				int dateComp = lrItem.getModifiedDate().compareTo(fbItem.getModifiedDate());
				if (dateComp == 0) {
					/* item has not changed; remove from lrItemSet */
					lrItemSet.remove(lrItem.getItemId());
				} else if (dateComp < 0) {
					/* fbItem is more recent; add to result */
					unsyncedItems.put(fbItem, e.getKey());
				}
			} else {
				/* Item exists in FB but not in LR */
				unsyncedItems.put(fbItem, e.getKey());
			}
		}
		/* Add remaining LR items to result */
		for (Entry<Long, Item> e : lrItemSet.entrySet()) {
			unsyncedItems.put(e.getValue(), "liferay");
		}
		
		return unsyncedItems;
	}
	
	private final Log _log = LogFactoryUtil.getLog(FirebaseItemSyncUtil.class);

}
