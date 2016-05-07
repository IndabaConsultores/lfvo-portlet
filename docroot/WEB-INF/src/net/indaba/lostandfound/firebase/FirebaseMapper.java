package net.indaba.lostandfound.firebase;

import java.util.Map;

import com.liferay.portal.kernel.model.BaseModel;

public abstract class FirebaseMapper<T extends BaseModel<T>> {

	public abstract Map<String, Object> toMap(T entity);
	
	public abstract T parseMap(Map<String, Object> entityMap);
	
}
