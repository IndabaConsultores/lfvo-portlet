package net.indaba.lostandfound.firebase;

import java.util.Map;

import com.liferay.portal.kernel.model.BaseModel;

public interface FirebaseMapper<T extends BaseModel<T>> {

	public Map<String, Object> toMap(T entity);
	
	public T parseMap(Map<String, Object> entityMap);
	
}
