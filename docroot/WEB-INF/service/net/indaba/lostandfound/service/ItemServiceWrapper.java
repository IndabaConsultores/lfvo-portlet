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

package net.indaba.lostandfound.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ItemService}.
 *
 * @author aritz
 * @see ItemService
 * @generated
 */
@ProviderType
public class ItemServiceWrapper implements ItemService,
	ServiceWrapper<ItemService> {
	public ItemServiceWrapper(ItemService itemService) {
		_itemService = itemService;
	}

	@Override
	public net.indaba.lostandfound.model.Item addOrUpdateItem(
		net.indaba.lostandfound.model.Item item,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemService.addOrUpdateItem(item, serviceContext);
	}

	@Override
	public net.indaba.lostandfound.model.Item addOrUpdateItem(
		net.indaba.lostandfound.model.Item item,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean updateFirebase)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemService.addOrUpdateItem(item, serviceContext, updateFirebase);
	}

	@Override
	public net.indaba.lostandfound.model.Item deleteItem(
		net.indaba.lostandfound.model.Item item)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemService.deleteItem(item);
	}

	@Override
	public net.indaba.lostandfound.model.Item deleteItem(
		net.indaba.lostandfound.model.Item item, boolean updateFirebase)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemService.deleteItem(item, updateFirebase);
	}

	@Override
	public net.indaba.lostandfound.model.Item deleteItem(long itemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemService.deleteItem(itemId);
	}

	@Override
	public net.indaba.lostandfound.model.Item deleteItem(long itemId,
		boolean updateFirebase)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemService.deleteItem(itemId, updateFirebase);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _itemService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _itemService.invokeMethod(name, parameterTypes, arguments);
	}

	@Override
	public ItemService getWrappedService() {
		return _itemService;
	}

	@Override
	public void setWrappedService(ItemService itemService) {
		_itemService = itemService;
	}

	private ItemService _itemService;
}