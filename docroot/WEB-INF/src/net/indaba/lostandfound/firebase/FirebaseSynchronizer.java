package net.indaba.lostandfound.firebase;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.util.portlet.PortletProps;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseSynchronizer {

	private static FirebaseSynchronizer instance = null;

	private Map<Class<?>, FirebaseService<?>> firebaseServices = 
			new LinkedHashMap<Class<?>, FirebaseService<?>>();
	
	private FirebaseSynchronizer() {
		super();
	}

	public static FirebaseSynchronizer getInstance() {
		if (instance == null) {
			instance = new FirebaseSynchronizer();
		}
		return instance;
	}

	public <T extends BaseModel<T>> void addService(Class<T> clazz,
			FirebaseService<T> service) {
		firebaseServices.put(clazz, service);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseModel<T>> FirebaseService<T> getService(
			Class<T> clazz) {
		FirebaseService<?> fs = firebaseServices.get(clazz);
		if (fs != null)
			return (FirebaseService<T>) firebaseServices.get(clazz);
		else
			return null;
	}
	
	public void resync(long date) {
		try {
			Firebase firebase = new Firebase(PortletProps.get("firebase.url") + "/_RESTART");
			firebase.put(String.valueOf(date));
		} catch (FirebaseException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
