package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.liferay.portal.kernel.model.BaseModel;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseTwoWayService<T extends BaseModel<T>> extends FirebaseService<T> {

	public FirebaseTwoWayService(String fbBaseURL, String fbModelSingular, String fbModelPlural,
			FirebaseMapper<T> mapper) {
		super(fbBaseURL, fbModelSingular, fbModelPlural, mapper);
	}

	public String add(T entity) {
		try {
			Firebase firebase = new Firebase(getFbURI());
			Map<String, Object> entityMap = getFbMapper().toMap(entity);
			FirebaseResponse response = firebase.post(entityMap);
			if (response.getCode() == 200) {
				_log.debug("Firebase create sucessful");
				return (String) response.getBody().get("name");
			} else {
				_log.error("Firebase create unsuccessful. Response code: " + response.getCode());
				return null;
			}
		} catch (FirebaseException | JacksonUtilityException | UnsupportedEncodingException e) {
			_log.error("Firebase create unsuccessful. Error : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public String getFirebaseKey(T entity) {
		try {
		Firebase firebase = new Firebase(getFbURI());

		firebase.addQuery("orderBy", getFbIdField());
		firebase.addQuery("equalTo", String.valueOf(entity.getPrimaryKeyObj()));
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
			_log.error("Firebase get key unsuccessfull. Error: " + response.getCode() + " " + response.getBody().get("error"));
		}
		} catch (FirebaseException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
