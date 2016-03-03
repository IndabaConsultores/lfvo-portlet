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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Item}.
 * </p>
 *
 * @author aritz
 * @see Item
 * @generated
 */
@ProviderType
public class ItemWrapper implements Item, ModelWrapper<Item> {
	public ItemWrapper(Item item) {
		_item = item;
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
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("itemId", getItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("publishDate", getPublishDate());
		attributes.put("objectId", getObjectId());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("description", getDescription());
		attributes.put("lat", getLat());
		attributes.put("lng", getLng());

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

		Date publishDate = (Date)attributes.get("publishDate");

		if (publishDate != null) {
			setPublishDate(publishDate);
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
	}

	@Override
	public java.lang.Object clone() {
		return new ItemWrapper((Item)_item.clone());
	}

	@Override
	public int compareTo(net.indaba.lostandfound.model.Item item) {
		return _item.compareTo(item);
	}

	/**
	* Returns the company ID of this item.
	*
	* @return the company ID of this item
	*/
	@Override
	public long getCompanyId() {
		return _item.getCompanyId();
	}

	/**
	* Returns the create date of this item.
	*
	* @return the create date of this item
	*/
	@Override
	public Date getCreateDate() {
		return _item.getCreateDate();
	}

	/**
	* Returns the description of this item.
	*
	* @return the description of this item
	*/
	@Override
	public java.lang.String getDescription() {
		return _item.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _item.getExpandoBridge();
	}

	/**
	* Returns the group ID of this item.
	*
	* @return the group ID of this item
	*/
	@Override
	public long getGroupId() {
		return _item.getGroupId();
	}

	/**
	* Returns the item ID of this item.
	*
	* @return the item ID of this item
	*/
	@Override
	public long getItemId() {
		return _item.getItemId();
	}

	/**
	* Returns the lat of this item.
	*
	* @return the lat of this item
	*/
	@Override
	public long getLat() {
		return _item.getLat();
	}

	/**
	* Returns the lng of this item.
	*
	* @return the lng of this item
	*/
	@Override
	public long getLng() {
		return _item.getLng();
	}

	/**
	* Returns the modified date of this item.
	*
	* @return the modified date of this item
	*/
	@Override
	public Date getModifiedDate() {
		return _item.getModifiedDate();
	}

	/**
	* Returns the name of this item.
	*
	* @return the name of this item
	*/
	@Override
	public java.lang.String getName() {
		return _item.getName();
	}

	/**
	* Returns the object ID of this item.
	*
	* @return the object ID of this item
	*/
	@Override
	public java.lang.String getObjectId() {
		return _item.getObjectId();
	}

	/**
	* Returns the primary key of this item.
	*
	* @return the primary key of this item
	*/
	@Override
	public long getPrimaryKey() {
		return _item.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _item.getPrimaryKeyObj();
	}

	/**
	* Returns the publish date of this item.
	*
	* @return the publish date of this item
	*/
	@Override
	public Date getPublishDate() {
		return _item.getPublishDate();
	}

	/**
	* Returns the type of this item.
	*
	* @return the type of this item
	*/
	@Override
	public java.lang.String getType() {
		return _item.getType();
	}

	/**
	* Returns the user ID of this item.
	*
	* @return the user ID of this item
	*/
	@Override
	public long getUserId() {
		return _item.getUserId();
	}

	/**
	* Returns the user uuid of this item.
	*
	* @return the user uuid of this item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _item.getUserUuid();
	}

	/**
	* Returns the uuid of this item.
	*
	* @return the uuid of this item
	*/
	@Override
	public java.lang.String getUuid() {
		return _item.getUuid();
	}

	@Override
	public int hashCode() {
		return _item.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _item.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _item.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _item.isNew();
	}

	@Override
	public void persist() {
		_item.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_item.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this item.
	*
	* @param companyId the company ID of this item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_item.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this item.
	*
	* @param createDate the create date of this item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_item.setCreateDate(createDate);
	}

	/**
	* Sets the description of this item.
	*
	* @param description the description of this item
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_item.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_item.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_item.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_item.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this item.
	*
	* @param groupId the group ID of this item
	*/
	@Override
	public void setGroupId(long groupId) {
		_item.setGroupId(groupId);
	}

	/**
	* Sets the item ID of this item.
	*
	* @param itemId the item ID of this item
	*/
	@Override
	public void setItemId(long itemId) {
		_item.setItemId(itemId);
	}

	/**
	* Sets the lat of this item.
	*
	* @param lat the lat of this item
	*/
	@Override
	public void setLat(long lat) {
		_item.setLat(lat);
	}

	/**
	* Sets the lng of this item.
	*
	* @param lng the lng of this item
	*/
	@Override
	public void setLng(long lng) {
		_item.setLng(lng);
	}

	/**
	* Sets the modified date of this item.
	*
	* @param modifiedDate the modified date of this item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_item.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this item.
	*
	* @param name the name of this item
	*/
	@Override
	public void setName(java.lang.String name) {
		_item.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_item.setNew(n);
	}

	/**
	* Sets the object ID of this item.
	*
	* @param objectId the object ID of this item
	*/
	@Override
	public void setObjectId(java.lang.String objectId) {
		_item.setObjectId(objectId);
	}

	/**
	* Sets the primary key of this item.
	*
	* @param primaryKey the primary key of this item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_item.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_item.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the publish date of this item.
	*
	* @param publishDate the publish date of this item
	*/
	@Override
	public void setPublishDate(Date publishDate) {
		_item.setPublishDate(publishDate);
	}

	/**
	* Sets the type of this item.
	*
	* @param type the type of this item
	*/
	@Override
	public void setType(java.lang.String type) {
		_item.setType(type);
	}

	/**
	* Sets the user ID of this item.
	*
	* @param userId the user ID of this item
	*/
	@Override
	public void setUserId(long userId) {
		_item.setUserId(userId);
	}

	/**
	* Sets the user uuid of this item.
	*
	* @param userUuid the user uuid of this item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_item.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this item.
	*
	* @param uuid the uuid of this item
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_item.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<net.indaba.lostandfound.model.Item> toCacheModel() {
		return _item.toCacheModel();
	}

	@Override
	public net.indaba.lostandfound.model.Item toEscapedModel() {
		return new ItemWrapper(_item.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _item.toString();
	}

	@Override
	public net.indaba.lostandfound.model.Item toUnescapedModel() {
		return new ItemWrapper(_item.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _item.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemWrapper)) {
			return false;
		}

		ItemWrapper itemWrapper = (ItemWrapper)obj;

		if (Validator.equals(_item, itemWrapper._item)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _item.getStagedModelType();
	}

	@Override
	public Item getWrappedModel() {
		return _item;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _item.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _item.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_item.resetOriginalValues();
	}

	private final Item _item;
}