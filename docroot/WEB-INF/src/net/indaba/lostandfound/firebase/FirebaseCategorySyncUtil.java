package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetCategory;
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

	private String FB_URI = "https://brilliant-torch-8285.firebaseio.com/categories";
	
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
		return true;
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
		//TODO parse description and title maps
		Map<String, Object> categoryMap = category.getModelAttributes();
		categoryMap.remove("categoryId");
		//Map<String, Object> objectMap = new HashMap<String, Object>();
		//objectMap.put(String.valueOf(category.getCategoryId()), categoryMap);
		return categoryMap;
	};

	private AssetCategory parseMap(Map<String, Object> map) {
		//TODO implement method body
		return null;
	};

	private final Log _log = LogFactoryUtil.getLog(FirebaseCategorySyncUtil.class);

}
