/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package net.indaba.lostandfound.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import net.indaba.lostandfound.service.ClpSerializer;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aritz
 */
@ProviderType
public class ItemClp extends BaseModelImpl<Item> implements Item {
	public ItemClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Item.class;
	}

	@Override
	public String getModelClassName() {
		return Item.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _itemId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setItemId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _itemId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("itemId", getItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectId", getObjectId());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("description", getDescription());
		attributes.put("lat", getLat());
		attributes.put("lng", getLng());
		attributes.put("field2", getField2());
		attributes.put("field3", getField3());
		attributes.put("field4", getField4());
		attributes.put("field5", getField5());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long itemId = (Long)attributes.get("itemId");

		if (itemId != null) {
			setItemId(itemId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String objectId = (String)attributes.get("objectId");

		if (objectId != null) {
			setObjectId(objectId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long lat = (Long)attributes.get("lat");

		if (lat != null) {
			setLat(lat);
		}

		Long lng = (Long)attributes.get("lng");

		if (lng != null) {
			setLng(lng);
		}

		Boolean field2 = (Boolean)attributes.get("field2");

		if (field2 != null) {
			setField2(field2);
		}

		Integer field3 = (Integer)attributes.get("field3");

		if (field3 != null) {
			setField3(field3);
		}

		Date field4 = (Date)attributes.get("field4");

		if (field4 != null) {
			setField4(field4);
		}

		String field5 = (String)attributes.get("field5");

		if (field5 != null) {
			setField5(field5);
		}

		_entityCacheEnabled = GetterUtil.getBoolean("entityCacheEnabled");
		_finderCacheEnabled = GetterUtil.getBoolean("finderCacheEnabled");
	}

	@Override
	public String getUuid() {
		return _uuid;
	}

	@Override
	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setUuid", String.class);

				method.invoke(_itemRemoteModel, uuid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getItemId() {
		return _itemId;
	}

	@Override
	public void setItemId(long itemId) {
		_itemId = itemId;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setItemId", long.class);

				method.invoke(_itemRemoteModel, itemId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setGroupId", long.class);

				method.invoke(_itemRemoteModel, groupId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setCompanyId", long.class);

				method.invoke(_itemRemoteModel, companyId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_itemRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setCreateDate", Date.class);

				method.invoke(_itemRemoteModel, createDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setModifiedDate", Date.class);

				method.invoke(_itemRemoteModel, modifiedDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getObjectId() {
		return _objectId;
	}

	@Override
	public void setObjectId(String objectId) {
		_objectId = objectId;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setObjectId", String.class);

				method.invoke(_itemRemoteModel, objectId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setName", String.class);

				method.invoke(_itemRemoteModel, name);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public void setType(String type) {
		_type = type;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setType", String.class);

				method.invoke(_itemRemoteModel, type);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public void setDescription(String description) {
		_description = description;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setDescription", String.class);

				method.invoke(_itemRemoteModel, description);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getLat() {
		return _lat;
	}

	@Override
	public void setLat(long lat) {
		_lat = lat;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setLat", long.class);

				method.invoke(_itemRemoteModel, lat);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getLng() {
		return _lng;
	}

	@Override
	public void setLng(long lng) {
		_lng = lng;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setLng", long.class);

				method.invoke(_itemRemoteModel, lng);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getField2() {
		return _field2;
	}

	@Override
	public boolean isField2() {
		return _field2;
	}

	@Override
	public void setField2(boolean field2) {
		_field2 = field2;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setField2", boolean.class);

				method.invoke(_itemRemoteModel, field2);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getField3() {
		return _field3;
	}

	@Override
	public void setField3(int field3) {
		_field3 = field3;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setField3", int.class);

				method.invoke(_itemRemoteModel, field3);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getField4() {
		return _field4;
	}

	@Override
	public void setField4(Date field4) {
		_field4 = field4;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setField4", Date.class);

				method.invoke(_itemRemoteModel, field4);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getField5() {
		return _field5;
	}

	@Override
	public void setField5(String field5) {
		_field5 = field5;

		if (_itemRemoteModel != null) {
			try {
				Class<?> clazz = _itemRemoteModel.getClass();

				Method method = clazz.getMethod("setField5", String.class);

				method.invoke(_itemRemoteModel, field5);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				Item.class.getName()));
	}

	public BaseModel<?> getItemRemoteModel() {
		return _itemRemoteModel;
	}

	public void setItemRemoteModel(BaseModel<?> itemRemoteModel) {
		_itemRemoteModel = itemRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _itemRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_itemRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() {
		if (this.isNew()) {
			ItemLocalServiceUtil.addItem(this);
		}
		else {
			ItemLocalServiceUtil.updateItem(this);
		}
	}

	@Override
	public Item toEscapedModel() {
		return (Item)ProxyUtil.newProxyInstance(Item.class.getClassLoader(),
			new Class[] { Item.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		ItemClp clone = new ItemClp();

		clone.setUuid(getUuid());
		clone.setItemId(getItemId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setObjectId(getObjectId());
		clone.setName(getName());
		clone.setType(getType());
		clone.setDescription(getDescription());
		clone.setLat(getLat());
		clone.setLng(getLng());
		clone.setField2(getField2());
		clone.setField3(getField3());
		clone.setField4(getField4());
		clone.setField5(getField5());

		return clone;
	}

	@Override
	public int compareTo(Item item) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), item.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemClp)) {
			return false;
		}

		ItemClp item = (ItemClp)obj;

		long primaryKey = item.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", itemId=");
		sb.append(getItemId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", objectId=");
		sb.append(getObjectId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", lat=");
		sb.append(getLat());
		sb.append(", lng=");
		sb.append(getLng());
		sb.append(", field2=");
		sb.append(getField2());
		sb.append(", field3=");
		sb.append(getField3());
		sb.append(", field4=");
		sb.append(getField4());
		sb.append(", field5=");
		sb.append(getField5());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(55);

		sb.append("<model><model-name>");
		sb.append("net.indaba.lostandfound.model.Item");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>itemId</column-name><column-value><![CDATA[");
		sb.append(getItemId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>objectId</column-name><column-value><![CDATA[");
		sb.append(getObjectId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lat</column-name><column-value><![CDATA[");
		sb.append(getLat());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lng</column-name><column-value><![CDATA[");
		sb.append(getLng());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>field2</column-name><column-value><![CDATA[");
		sb.append(getField2());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>field3</column-name><column-value><![CDATA[");
		sb.append(getField3());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>field4</column-name><column-value><![CDATA[");
		sb.append(getField4());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>field5</column-name><column-value><![CDATA[");
		sb.append(getField5());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _itemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _objectId;
	private String _name;
	private String _type;
	private String _description;
	private long _lat;
	private long _lng;
	private boolean _field2;
	private int _field3;
	private Date _field4;
	private String _field5;
	private BaseModel<?> _itemRemoteModel;
	private Class<?> _clpSerializerClass = net.indaba.lostandfound.service.ClpSerializer.class;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
}