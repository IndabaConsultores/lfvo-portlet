package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.liferay.portal.kernel.model.BaseModel;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseOneWayService<T extends BaseModel<T>> extends FirebaseService<T> {

	public FirebaseOneWayService(String fbBaseURL, String fbModelSingular, String fbModelPlural,
			FirebaseMapper<T> mapper) {
		super(fbBaseURL, fbModelSingular, fbModelPlural, mapper);
	}

	@Override
	public String add(T entity) {

		try {
			Firebase firebase = new Firebase(getFbURI());
			Map<String, Object> entityMap = getFbMapper().toMap(entity);
			FirebaseResponse response = firebase.put("/" + entity.getPrimaryKeyObj(), entityMap);
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

	@Override
	public String getFirebaseKey(T entity) {
		return String.valueOf(entity.getPrimaryKeyObj());
	}

}
