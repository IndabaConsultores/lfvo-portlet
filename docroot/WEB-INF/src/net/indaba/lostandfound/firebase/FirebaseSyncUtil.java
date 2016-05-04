package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.util.portlet.PortletProps;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseSyncUtil {

	private final String FB_URI = PortletProps.get("firebase.url");
	private long syncCheckDate;

	private Map<Item, String> unsyncedItems;
	private Map<LFImage, String> unsyncedImages;
	private Map<AssetCategory, String> unsyncedCats;
	private Map<AssetTag, String> unsyncedTags;
	private Map<MBMessage, String> unsyncedMsgs;
	private Map<Group, String> unsyncedGrps;

	private FirebaseItemSyncUtil itemUtil = FirebaseItemSyncUtil.getInstance();
	private FirebaseLFImageSyncUtil imageUtil = FirebaseLFImageSyncUtil.getInstance();
	private FirebaseCategorySyncUtil catUtil = FirebaseCategorySyncUtil.getInstance();
	private FirebaseMBMessageSyncUtil msgUtil = FirebaseMBMessageSyncUtil.getInstance();
	private FirebaseTagSyncUtil tagUtil = FirebaseTagSyncUtil.getInstance();
	private FirebaseGroupSyncUtil groupUtil = FirebaseGroupSyncUtil.getInstance();

	private static FirebaseSyncUtil instance = null;
	
	private FirebaseSyncUtil() {};

	public static FirebaseSyncUtil getInstance() {
		if (instance == null) {
			instance = new FirebaseSyncUtil();
		}
		return instance;
	}

	public void updateUnsyncedItems() throws UnsupportedEncodingException, FirebaseException {

		long lastSyncDate;
		Firebase firebase = new Firebase(FB_URI);
		FirebaseResponse response = firebase.get("_TIMESTAMP");
		Map<String, Object> responseBody = response.getBody();
		if (responseBody.get("Sync") == null) {
			lastSyncDate = 0;
		} else {
			lastSyncDate = (long) responseBody.get("Sync");
		}
		
		syncCheckDate = System.currentTimeMillis();
		unsyncedItems = itemUtil.getUnsyncedItemsSince(lastSyncDate);
		unsyncedImages = imageUtil.getUnsyncedImagesSince(lastSyncDate);
		unsyncedCats = catUtil.getUnsyncedCatsSince(lastSyncDate);
		unsyncedTags = tagUtil.getUnsyncedTagsSince(lastSyncDate);
		unsyncedMsgs = msgUtil.getUnsyncedMsgsSince(lastSyncDate);
		unsyncedGrps = groupUtil.getUnsyncedGroups(lastSyncDate);
				
		System.out.println("Items out of sync: " + unsyncedItems.size());
		System.out.println("Images out of sync: " + unsyncedImages.size());
		System.out.println("Categories out of sync: " + unsyncedCats.size());
		System.out.println("Tags out of sync: " + unsyncedTags.size());
		System.out.println("Messages out of sync: " + unsyncedMsgs.size());
		System.out.println("Group sites out of sync: " + unsyncedGrps.size());
	}

	public void resyncItems(ServiceContext serviceContext)
			throws UnsupportedEncodingException, FirebaseException, JacksonUtilityException, PortalException {
		
		
		/* Push items to Firebase */
		for (Entry<Item, String> e : unsyncedItems.entrySet()) {
			Item item = e.getKey();
			String fbKey = e.getValue();
			if (fbKey.equals("liferay")) {
				itemUtil.addOrUpdateItem(item);
			} else {
				boolean isNew = item.isNew();
				item = ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext);
				if (isNew) {
					itemUtil.update(item, fbKey);
				}
			}
		}
		unsyncedItems.clear();

		for (Entry<MBMessage, String> e : unsyncedMsgs.entrySet()) {
			MBMessage msg = e.getKey();
			String fbKey = e.getValue();
			if (fbKey.equals("liferay")) {
				msgUtil.addOrUpdate(msg);
			} else {
				boolean isNew = msg.isNew();
				if (isNew) {
					msg = MBMessageLocalServiceUtil
							.addDiscussionMessage(serviceContext.getUserId(), "firebase", msg.getGroupId(), 
									Item.class.getName(), msg.getClassPK(), 0, 0, 
									msg.getSubject(), msg.getBody(), serviceContext);
					msgUtil.update(msg, fbKey);
				} else {
					msg = MBMessageLocalServiceUtil.updateMBMessage(msg);
				}
			}
		}
		unsyncedMsgs.clear();

		for (Entry<AssetTag, String> e : unsyncedTags.entrySet()) {
			AssetTag tag = e.getKey();
			String fbKey = e.getValue();
			if (fbKey.equals("liferay")) {
				tagUtil.addOrUpdate(tag);
			} else {
				// should never be the case
			}
		}
		unsyncedTags.clear();

		for (Entry<AssetCategory, String> e : unsyncedCats.entrySet()) {
			AssetCategory category = e.getKey();
			String fbKey = e.getValue();
			if (fbKey.equals("liferay")) {
				catUtil.addOrUpdate(category);
			} else  {
				// should never be the case
			}
		}
		unsyncedCats.clear();
		
		for (Entry<Group, String> e : unsyncedGrps.entrySet()) {
			Group group = e.getKey();
			String fbKey = e.getValue();
			if (fbKey.equals("liferay")) {
				groupUtil.addOrUpdate(group);
			} else  {
				// should never be the case
			}
		}
		unsyncedGrps.clear();

		Map<String, Object> dateMap = new HashMap<String, Object>();
		if (syncCheckDate != 0) {
			dateMap.put("Sync", syncCheckDate);
			Firebase firebase = new Firebase(FB_URI);
			firebase.patch("/_TIMESTAMP", dateMap);
		}

	}

	private final Log _log = LogFactoryUtil.getLog(FirebaseSyncUtil.class);

}
