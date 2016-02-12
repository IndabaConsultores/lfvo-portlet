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

package net.indaba.lostandfound.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.base.ItemLocalServiceBaseImpl;

/**
 * The implementation of the item local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link net.indaba.lostandfound.service.ItemLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author aritz
 * @see ItemLocalServiceBaseImpl
 * @see net.indaba.lostandfound.service.ItemLocalServiceUtil
 */
@ProviderType
public class ItemLocalServiceImpl extends ItemLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link
	 * net.indaba.lostandfound.service.ItemLocalServiceUtil} to access the item
	 * local service.
	 */

	@Override
	public Item addOrUpdateItem(Item item, ServiceContext serviceContext) throws PortalException {
		_log.debug("addOrUpdateItem");
		if(item.isNew())
			item.setItemId(CounterLocalServiceUtil.increment());
		item = super.updateItem(item);
		
		updateAsset(serviceContext.getUserId(), item, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), serviceContext.getAssetLinkEntryIds());

		return item;
	}

	private void updateAsset(long userId, Item item, long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds) throws PortalException {

		
		try {
			AssetEntry assetEntry = assetEntryLocalService.updateEntry(userId, item.getGroupId(), Item.class.getName(),
					item.getItemId(), assetCategoryIds, assetTagNames);
			assetLinkLocalService.updateLinks(userId, assetEntry.getEntryId(), assetLinkEntryIds,
					AssetLinkConstants.TYPE_RELATED);
		} catch (Exception e) {
			_log.error("Error updating Items asset", e);
		}
		
	}
	
	private final Log _log = LogFactoryUtil.getLog(this.getClass());

}