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
	public net.indaba.lostandfound.model.Item addItem(java.lang.String name) {
		return _itemService.addItem(name);
	}

	@Override
	public net.indaba.lostandfound.model.Item getItem(long itemId) {
		return _itemService.getItem(itemId);
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
	public net.indaba.lostandfound.model.Item removeItem(long itemId) {
		return _itemService.removeItem(itemId);
	}

	@Override
	public java.lang.String test(java.lang.String in) {
		return _itemService.test(in);
	}

	@Override
	public net.indaba.lostandfound.model.Item updateItem(long itemId,
		java.lang.String name) {
		return _itemService.updateItem(itemId, name);
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