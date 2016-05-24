package net.indaba.lostandfound.firebase;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.util.portlet.PortletProps;

public class FirebaseServiceFactory {

	private final static String FB_BASE_URI = PortletProps.get("firebase.url");

	public static enum SYNC_TYPE {
		ONE_WAY, TWO_WAY
	}

	private FirebaseServiceFactory() {
		super();
	}

	/**
	 * Creates a FirebaseService of the given type
	 * 
	 * @param type
	 *            One of the following: {"OneWay", "TwoWay"}
	 * @param singularName
	 *            Singular name for the entity model
	 * @param pluralName
	 *            Plural name for the entity model
	 * @param mapper
	 *            A FirebaseMapper for the entity model
	 * @return FirebaseService
	 */
	public static <T extends BaseModel<T>> FirebaseService<T> createService(
			SYNC_TYPE type, String singularName,
			String pluralName, FirebaseMapper<T> mapper) {
		switch (type) {
		case ONE_WAY:
			return new FirebaseOneWayService<T>(FB_BASE_URI, singularName,
					pluralName, mapper);
		case TWO_WAY:
			return new FirebaseTwoWayService<T>(FB_BASE_URI, singularName,
					pluralName, mapper);
		}
		return null;
	}

}
