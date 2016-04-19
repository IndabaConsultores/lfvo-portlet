package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.util.portlet.PortletProps;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 * Firebase sync utility class. Considers objects as Liferay owned; uses the
 * object primary key as Firebase key
 * 
 * @author yzhan
 *
 */
public class FirebaseGroupSyncUtil {

	private static FirebaseGroupSyncUtil instance;

	private String FB_URI = PortletProps.get("firebase.url") + "/offices";

	private FirebaseGroupSyncUtil() {
		super();
	}

	public static FirebaseGroupSyncUtil getInstance() {
		if (instance == null) {
			instance = new FirebaseGroupSyncUtil();
		}
		return instance;
	}

	public boolean isSyncEnabled() {
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}

	public void add(Group group) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		addOrUpdate(group);
	}

	public void update(Group group) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		addOrUpdate(group);
	}

	public void addOrUpdate(Group group)
			throws FirebaseException, JacksonUtilityException, UnsupportedEncodingException {
		Firebase firebase = new Firebase(FB_URI);
		Map<String, Object> map = toMap(group);
		FirebaseResponse response = firebase.patch("/" + group.getPrimaryKey(), map);
		if (response.getCode() == 200) {
			_log.debug("Firebase create/update sucessful");
		} else {
			_log.debug("Firebase create/update unsuccessful. Response code: " + response.getCode());
		}
	}

	public void delete(Group group) throws FirebaseException, UnsupportedEncodingException, JacksonUtilityException {
		Firebase firebase = new Firebase(FB_URI);

		FirebaseResponse response;
		response = firebase.delete("/" + group.getPrimaryKey());
		if (response.getCode() == 200) {
			_log.debug("Firebase delete sucessful");
		} else {
			_log.debug("Firebase delete unsuccessful. Response code: " + response.getCode());
		}
	}

	private Map<String, Object> toMap(Group group) {
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("name", group.getNameMap());
		groupMap.put("description", group.getDescriptionMap());
		groupMap.put("companyId", group.getCompanyId());
		return groupMap;
	};

	private List<Group> getLiferayGroupsAfter(long liferayTS) {
		// TODO method
		return null;
	}

	public Map<Group, String> getUnsyncedGroups(long date) throws UnsupportedEncodingException, FirebaseException {
		Map<Group, String> unsynced = new HashMap<Group, String>();
		// TODO instead of unsynced, a single push may be better
		return unsynced;
	}

	private final Log _log = LogFactoryUtil.getLog(FirebaseGroupSyncUtil.class);

}
