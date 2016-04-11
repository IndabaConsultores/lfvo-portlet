package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.portlet.PortletProps;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 * Firebase sync utility class.
 * Considers objects as Liferay owned; 
 * uses the object primary key as Firebase key
 * @author yzhan
 *
 */
public class FirebaseTagSyncUtil {

	private static FirebaseTagSyncUtil instance;

	private String FB_URI = PortletProps.get("firebase.url") + "/tags";
	
	private FirebaseTagSyncUtil() {
		super();
	}

	public static FirebaseTagSyncUtil getInstance() {
		if (instance == null) {
			instance = new FirebaseTagSyncUtil();
		}
		return instance;
	}
	
	public boolean isSyncEnabled() {
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}
	
	public void add(AssetTag tag) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);
		Map<String, Object> objectMap = toMap(tag);
		FirebaseResponse response = firebase.put("/" + tag.getTagId(),objectMap);
		if (response.getCode() == 200) {
			_log.debug("Firebase create sucessful");
		} else {
			_log.debug("Firebase create unsuccessful. Response code: " + response.getCode());
		}
	}
	
	public void update(AssetTag tag) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		update(tag, String.valueOf(tag.getTagId()));
	}
	
	public void update(AssetTag tag, String key) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);
		Map<String, Object> map = toMap(tag);
		FirebaseResponse response = firebase.patch("/" + key, map);
		if (response.getCode() == 200) {
			_log.debug("Firebase update sucessful");
		} else {
			_log.debug("Firebase update unsuccessful. Response code: " + response.getCode());
		}
	}

	public void addOrUpdate(AssetTag tag)
			throws FirebaseException, JacksonUtilityException, UnsupportedEncodingException {
		String key = getFirebaseKey(tag);
		if (key != null) { /* tag exists already in Firebase: update */
			update(tag, key);
		} else { /* tag does not exist in Firebase: create */
			add(tag);
		}
	}

	public void delete(AssetTag tag)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);

		String key = getFirebaseKey(tag);
		FirebaseResponse response;
		if (key != null) {
			response = firebase.delete("/" + key);
			if (response.getCode() == 200) {
				_log.debug("Firebase delete sucessful");
			} else {
				_log.debug("Firebase delete unsuccessful. Response code: " + response.getCode());
			}
		} else {
			_log.debug("Could not find tag with id " + tag.getPrimaryKeyObj());
		}
	}

	private String getFirebaseKey(AssetTag tag) throws FirebaseException, UnsupportedEncodingException {
		Firebase firebase = new Firebase(FB_URI);

		FirebaseResponse response = 
				firebase.get("/" + String.valueOf(tag.getTagId()));
		if (response.getCode() == 200) {
			Map<String, Object> responseMap = response.getBody();
			if (responseMap.size() > 0) {
				return String.valueOf(tag.getTagId());
			} else {
				return null;
			}
		} else {
			_log.debug("Firebase get key unsuccessfull. Response code: " + response.getCode());
		}
		return null;
	}

	private Map<String, Object> toMap(AssetTag tag) {
		//TODO parse description and title maps
		Map<String, Object> tagMap = tag.getModelAttributes();
		tagMap.remove("tagId");
		//Map<String, Object> objectMap = new HashMap<String, Object>();
		//objectMap.put(String.valueOf(tag.getTagId()), tagMap);
		return tagMap;
	}

	private AssetTag parseMap(Map<String, Object> map) {
		//TODO implement method body
		return null;
	}
	
	private List<AssetTag> getLiferayTagsAfter(long liferayTS) {
		/* Get Liferay categories that were added/updated after last update time */
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(AssetTag.class)
				.add(PropertyFactoryUtil.forName("modifiedDate").gt(new Date(liferayTS)));
		return AssetCategoryLocalServiceUtil.dynamicQuery(query);
	}
	
	public Map<AssetTag, String> getUnsyncedTagsSince(long date) throws UnsupportedEncodingException, FirebaseException {
		Map<AssetTag, String> unsynced = new HashMap<AssetTag, String>();

		/* Get Liferay categories that were added/updated after last sync time */
		List<AssetTag> assetCategories = getLiferayTagsAfter(date);
		/* Firebase categories cannot be modified by Firebase app */
		
		for (AssetTag ac : assetCategories) {
			if (ac.getModifiedDate().compareTo(new Date(date)) > 0) {
				unsynced.put(ac, "liferay");
			}
		}
		return unsynced;
	}

	private final Log _log = LogFactoryUtil.getLog(FirebaseTagSyncUtil.class);

}
