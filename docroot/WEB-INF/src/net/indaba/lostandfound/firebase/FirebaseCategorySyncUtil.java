package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetCategory;
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
public class FirebaseCategorySyncUtil {

	private static FirebaseCategorySyncUtil instance;

	private String FB_URI = PortletProps.get("firebase.url") + "/categories";
	
	private FirebaseCategorySyncUtil() {
		super();
	}

	public static FirebaseCategorySyncUtil getInstance() {
		if (instance == null) {
			instance = new FirebaseCategorySyncUtil();
		}
		return instance;
	}
	
	public boolean isSyncEnabled() {
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}
	
	public void add(AssetCategory category) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);
		Map<String, Object> objectMap = toMap(category);
		FirebaseResponse response = firebase.patch("/" + category.getCategoryId(),objectMap);
		if (response.getCode() == 200) {
			_log.debug("Firebase create sucessful");
		} else {
			_log.debug("Firebase create unsuccessful. Response code: " + response.getCode());
		}
	}
	
	public void update(AssetCategory category) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		update(category, String.valueOf(category.getCategoryId()));
	}
	
	public void update(AssetCategory category, String key) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);
		Map<String, Object> map = toMap(category);
		FirebaseResponse response = firebase.patch("/" + key, map);
		if (response.getCode() == 200) {
			_log.debug("Firebase update sucessful");
		} else {
			_log.debug("Firebase update unsuccessful. Response code: " + response.getCode());
		}
	}

	public void addOrUpdate(AssetCategory category)
			throws FirebaseException, JacksonUtilityException, UnsupportedEncodingException {
		String key = getFirebaseKey(category);
		if (key != null) { /* Category exists already in Firebase: update */
			update(category, key);
		} else { /* Category does not exist in Firebase: create */
			add(category);
		}
	}

	public void delete(AssetCategory category)
			throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);

		String key = getFirebaseKey(category);
		FirebaseResponse response;
		if (key != null) {
			response = firebase.delete("/" + key);
			if (response.getCode() == 200) {
				_log.debug("Firebase delete sucessful");
			} else {
				_log.debug("Firebase delete unsuccessful. Response code: " + response.getCode());
			}
		} else {
			_log.debug("Could not find category with id " + category.getPrimaryKeyObj());
		}
	}

	private String getFirebaseKey(AssetCategory category) throws FirebaseException, UnsupportedEncodingException {
		Firebase firebase = new Firebase(FB_URI);

		FirebaseResponse response = 
				firebase.get("/" + String.valueOf(category.getCategoryId()));
		if (response.getCode() == 200) {
			Map<String, Object> responseMap = response.getBody();
			if (responseMap.size() > 0) {
				return String.valueOf(category.getCategoryId());
			} else {
				return null;
			}
		} else {
			_log.debug("Firebase get key unsuccessfull. Response code: " + response.getCode());
		}
		return null;
	}

	private Map<String, Object> toMap(AssetCategory category) {
		Map<String, Object> categoryMap = new HashMap<String, Object>();
		categoryMap.put("title", category.getTitleMap());
		categoryMap.put("name", category.getName());
		categoryMap.put("description", category.getDescriptionMap());
		categoryMap.put("office", category.getGroupId());
		categoryMap.put("companyId", category.getCompanyId());
		categoryMap.put("createDate", category.getCreateDate());
		categoryMap.put("modifiedDate", category.getModifiedDate());
		return categoryMap;
	};

	private AssetCategory parseMap(Map<String, Object> map) {
		//TODO implement method body
		return null;
	};
	
	private List<AssetCategory> getLiferayCatsAfter(long liferayTS) {
		/* Get Liferay categories that were added/updated after last update time */
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(AssetCategory.class)
				.add(PropertyFactoryUtil.forName("modifiedDate").gt(new Date(liferayTS)));
		return AssetCategoryLocalServiceUtil.dynamicQuery(query);
	}
	
	public Map<AssetCategory, String> getUnsyncedCatsSince(long date) throws UnsupportedEncodingException, FirebaseException {
		Map<AssetCategory, String> unsynced = new HashMap<AssetCategory, String>();

		/* Get Liferay categories that were added/updated after last sync time */
		List<AssetCategory> assetCategories = getLiferayCatsAfter(date);
		/* Firebase categories cannot be modified by Firebase app */
		
		for (AssetCategory ac : assetCategories) {
			if (ac.getModifiedDate().compareTo(new Date(date)) > 0) {
				unsynced.put(ac, "liferay");
			}
		}
		return unsynced;
	}

	private final Log _log = LogFactoryUtil.getLog(FirebaseCategorySyncUtil.class);

}
