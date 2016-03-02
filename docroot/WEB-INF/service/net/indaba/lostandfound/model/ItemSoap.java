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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link net.indaba.lostandfound.service.http.ItemServiceSoap}.
 *
 * @author aritz
 * @see net.indaba.lostandfound.service.http.ItemServiceSoap
 * @generated
 */
@ProviderType
public class ItemSoap implements Serializable {
	public static ItemSoap toSoapModel(Item model) {
		ItemSoap soapModel = new ItemSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setItemId(model.getItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPublishDate(model.getPublishDate());
		soapModel.setObjectId(model.getObjectId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setDescription(model.getDescription());
		soapModel.setLat(model.getLat());
		soapModel.setLng(model.getLng());

		return soapModel;
	}

	public static ItemSoap[] toSoapModels(Item[] models) {
		ItemSoap[] soapModels = new ItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ItemSoap[][] toSoapModels(Item[][] models) {
		ItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ItemSoap[] toSoapModels(List<Item> models) {
		List<ItemSoap> soapModels = new ArrayList<ItemSoap>(models.size());

		for (Item model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ItemSoap[soapModels.size()]);
	}

	public ItemSoap() {
	}

	public long getPrimaryKey() {
		return _itemId;
	}

	public void setPrimaryKey(long pk) {
		setItemId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getItemId() {
		return _itemId;
	}

	public void setItemId(long itemId) {
		_itemId = itemId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public Date getPublishDate() {
		return _publishDate;
	}

	public void setPublishDate(Date publishDate) {
		_publishDate = publishDate;
	}

	public String getObjectId() {
		return _objectId;
	}

	public void setObjectId(String objectId) {
		_objectId = objectId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public long getLat() {
		return _lat;
	}

	public void setLat(long lat) {
		_lat = lat;
	}

	public long getLng() {
		return _lng;
	}

	public void setLng(long lng) {
		_lng = lng;
	}

	private String _uuid;
	private long _itemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private Date _publishDate;
	private String _objectId;
	private String _name;
	private String _type;
	private String _description;
	private long _lat;
	private long _lng;
}