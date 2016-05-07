package net.indaba.lostandfound.firebase;

import com.liferay.portal.kernel.model.BaseModel;

public class FirebaseServiceFactory {

	private FirebaseServiceFactory() {
		super();
	}
	
	/**
	 * Creates a FirebaseService of the given type
	 * @param type One of the following: {"OneWay", "TwoWay"}
	 * @param singularName Singular name for the entity model
	 * @param pluralName Plural name for the entity model
	 * @param mapper A FirebaseMapper for the entity model
	 * @return FirebaseService
	 */
	public static <T extends BaseModel<T>> FirebaseService<T> getService(String type, String singularName, 
			String pluralName, FirebaseMapper<T> mapper) {
		switch (type) {
		case "OneWay":
			return new FirebaseOneWayService<T>(singularName, pluralName, mapper);
		case "TwoWay":
			return new FirebaseTwoWayService<T>(singularName, pluralName, mapper);
		}
		return null;
	}

}
