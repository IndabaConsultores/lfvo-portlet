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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.service.base.ItemServiceBaseImpl;

/**
 * The implementation of the item remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link net.indaba.lostandfound.service.ItemService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials because this service
 * can be accessed remotely.
 * </p>
 *
 * @author aritz
 * @see ItemServiceBaseImpl
 * @see net.indaba.lostandfound.service.ItemServiceUtil
 */
@ProviderType
public class ItemServiceImpl extends ItemServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link
	 * net.indaba.lostandfound.service.ItemServiceUtil} to access the item
	 * remote service.
	 */

	public Item addOrUpdateItem(Item item, ServiceContext serviceContext)
			throws PortalException {
		return ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext);
	}

	public Item deleteItem(long itemId, ServiceContext serviceContext)
			throws PortalException {
		return ItemLocalServiceUtil.deleteItem(itemId, serviceContext);
	}

	public Item deleteItem(Item item, ServiceContext serviceContext)
			throws PortalException {
		return ItemLocalServiceUtil.deleteItem(item, serviceContext);
	}
}