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

package net.indaba.lostandfound.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import net.indaba.lostandfound.model.Item;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Item in entity cache.
 *
 * @author aritz
 * @see Item
 * @generated
 */
@ProviderType
public class ItemCacheModel implements CacheModel<Item>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemCacheModel)) {
			return false;
		}

		ItemCacheModel itemCacheModel = (ItemCacheModel)obj;

		if (itemId == itemCacheModel.itemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, itemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", itemId=");
		sb.append(itemId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", objectId=");
		sb.append(objectId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lat=");
		sb.append(lat);
		sb.append(", lng=");
		sb.append(lng);
		sb.append(", field2=");
		sb.append(field2);
		sb.append(", field3=");
		sb.append(field3);
		sb.append(", field4=");
		sb.append(field4);
		sb.append(", field5=");
		sb.append(field5);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Item toEntityModel() {
		ItemImpl itemImpl = new ItemImpl();

		if (uuid == null) {
			itemImpl.setUuid(StringPool.BLANK);
		}
		else {
			itemImpl.setUuid(uuid);
		}

		itemImpl.setItemId(itemId);
		itemImpl.setGroupId(groupId);
		itemImpl.setCompanyId(companyId);
		itemImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			itemImpl.setCreateDate(null);
		}
		else {
			itemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			itemImpl.setModifiedDate(null);
		}
		else {
			itemImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (objectId == null) {
			itemImpl.setObjectId(StringPool.BLANK);
		}
		else {
			itemImpl.setObjectId(objectId);
		}

		if (name == null) {
			itemImpl.setName(StringPool.BLANK);
		}
		else {
			itemImpl.setName(name);
		}

		if (type == null) {
			itemImpl.setType(StringPool.BLANK);
		}
		else {
			itemImpl.setType(type);
		}

		if (description == null) {
			itemImpl.setDescription(StringPool.BLANK);
		}
		else {
			itemImpl.setDescription(description);
		}

		itemImpl.setLat(lat);
		itemImpl.setLng(lng);
		itemImpl.setField2(field2);
		itemImpl.setField3(field3);

		if (field4 == Long.MIN_VALUE) {
			itemImpl.setField4(null);
		}
		else {
			itemImpl.setField4(new Date(field4));
		}

		if (field5 == null) {
			itemImpl.setField5(StringPool.BLANK);
		}
		else {
			itemImpl.setField5(field5);
		}

		itemImpl.resetOriginalValues();

		return itemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		itemId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		objectId = objectInput.readUTF();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
		description = objectInput.readUTF();
		lat = objectInput.readLong();
		lng = objectInput.readLong();
		field2 = objectInput.readBoolean();
		field3 = objectInput.readInt();
		field4 = objectInput.readLong();
		field5 = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(itemId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (objectId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(objectId);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(lat);
		objectOutput.writeLong(lng);
		objectOutput.writeBoolean(field2);
		objectOutput.writeInt(field3);
		objectOutput.writeLong(field4);

		if (field5 == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(field5);
		}
	}

	public String uuid;
	public long itemId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String objectId;
	public String name;
	public String type;
	public String description;
	public long lat;
	public long lng;
	public boolean field2;
	public int field3;
	public long field4;
	public String field5;
}