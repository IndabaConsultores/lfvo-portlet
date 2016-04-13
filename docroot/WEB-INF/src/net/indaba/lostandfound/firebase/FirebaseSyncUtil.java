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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.util.portlet.PortletProps;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.model.impl.ItemImpl;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseSyncUtil {

	private final String FB_URI = PortletProps.get("firebase.url");
	private long lastSyncDate;

	private Map<Item, String> unsyncedItems;
	private Map<LFImage, String> unsyncedImages;
	private Map<AssetCategory, String> unsyncedCats;
	private Map<AssetTag, String> unsyncedTags;
	private Map<MBMessage, String> unsyncedMsgs;

	private FirebaseItemSyncUtil itemUtil = FirebaseItemSyncUtil.getInstance();
	private FirebaseLFImageSyncUtil imageUtil = FirebaseLFImageSyncUtil.getInstance();
	private FirebaseCategorySyncUtil catUtil = FirebaseCategorySyncUtil.getInstance();
	private FirebaseMBMessageSyncUtil msgUtil = FirebaseMBMessageSyncUtil.getInstance();
	private FirebaseTagSyncUtil tagUtil = FirebaseTagSyncUtil.getInstance();

	private static FirebaseSyncUtil instance = null;
	
	private FirebaseSyncUtil() {};

	public static FirebaseSyncUtil getInstance() {
		if (instance == null) {
			instance = new FirebaseSyncUtil();
		}
		return instance;
	}

	public void updateUnsyncedItems() throws UnsupportedEncodingException, FirebaseException {
		Firebase firebase = new Firebase(FB_URI);
		FirebaseResponse response = firebase.get("_TIMESTAMP");
		Map<String, Object> responseBody = response.getBody();
		if (responseBody.get("Sync") == null) {
			lastSyncDate = 0;
		}
		lastSyncDate = (long) responseBody.get("Sync");
		
		unsyncedItems = itemUtil.getUnsyncedItemsSince(lastSyncDate);
		unsyncedImages = imageUtil.getUnsyncedImagesSince(lastSyncDate);
		unsyncedCats = catUtil.getUnsyncedCatsSince(lastSyncDate);
		unsyncedTags = tagUtil.getUnsyncedTagsSince(lastSyncDate);
		unsyncedMsgs = msgUtil.getUnsyncedMsgsSince(lastSyncDate);
	}

	public void resyncItems()
			throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException, PortalException {
		/* Push items to Firebase */
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setUserId(25602);
		for (Entry<Item, String> e : unsyncedItems.entrySet()) {
			Item item = e.getKey();
			String origin = e.getValue();
			if (origin.equals("liferay")) {
				itemUtil.addOrUpdateItem(item);
			} else if (origin.equals("firebase")) {
				boolean isNew = item.isNew();
				item = ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext);
				if (isNew) {
					itemUtil.update(item);
				}
			}
		}
		unsyncedItems.clear();

		for (Entry<MBMessage, String> e : unsyncedMsgs.entrySet()) {
			MBMessage item = e.getKey();
			String origin = e.getValue();
			if (origin.equals("liferay")) {
				msgUtil.addOrUpdate(item);
			} else if (origin.equals("firebase")) {
				boolean isNew = item.isNew();
				item = MBMessageLocalServiceUtil.updateMBMessage(item);
				if (isNew) {
					msgUtil.update(item);
				}
			}
		}
		unsyncedMsgs.clear();

		for (Entry<AssetTag, String> e : unsyncedTags.entrySet()) {
			AssetTag item = e.getKey();
			String origin = e.getValue();
			if (origin.equals("liferay")) {
				tagUtil.addOrUpdate(item);
			} else if (origin.equals("firebase")) {
				// should never be the case
			}
		}
		unsyncedTags.clear();

		for (Entry<AssetCategory, String> e : unsyncedCats.entrySet()) {
			AssetCategory item = e.getKey();
			String origin = e.getValue();
			if (origin.equals("liferay")) {
				catUtil.addOrUpdate(item);
			} else if (origin.equals("firebase")) {
				// should never be the case
			}
		}
		unsyncedCats.clear();

		Map<String, Object> dateMap = new HashMap<String, Object>();
		if (lastSyncDate != 0)
			dateMap.put("Sync", lastSyncDate);
		Firebase firebase = new Firebase(FB_URI);
		firebase.patch("/_TIMESTAMP", dateMap);

		Item item;
		boolean isNew;

	}

	private final Log _log = LogFactoryUtil.getLog(FirebaseItemSyncUtil.class);

}
