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

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import net.indaba.lostandfound.model.LFImage;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LFImage in entity cache.
 *
 * @author aritz
 * @see LFImage
 * @generated
 */
@ProviderType
public class LFImageCacheModel implements CacheModel<LFImage>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LFImageCacheModel)) {
			return false;
		}

		LFImageCacheModel lfImageCacheModel = (LFImageCacheModel)obj;

		if (lfImageId == lfImageCacheModel.lfImageId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, lfImageId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", lfImageId=");
		sb.append(lfImageId);
		sb.append(", itemId=");
		sb.append(itemId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LFImage toEntityModel() {
		LFImageImpl lfImageImpl = new LFImageImpl();

		if (uuid == null) {
			lfImageImpl.setUuid(StringPool.BLANK);
		}
		else {
			lfImageImpl.setUuid(uuid);
		}

		lfImageImpl.setLfImageId(lfImageId);
		lfImageImpl.setItemId(itemId);

		if (createDate == Long.MIN_VALUE) {
			lfImageImpl.setCreateDate(null);
		}
		else {
			lfImageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			lfImageImpl.setModifiedDate(null);
		}
		else {
			lfImageImpl.setModifiedDate(new Date(modifiedDate));
		}

		lfImageImpl.resetOriginalValues();

		return lfImageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		lfImageId = objectInput.readLong();

		itemId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
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

		objectOutput.writeLong(lfImageId);

		objectOutput.writeLong(itemId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public String uuid;
	public long lfImageId;
	public long itemId;
	public long createDate;
	public long modifiedDate;
}