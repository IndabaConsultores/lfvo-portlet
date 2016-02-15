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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

/**
 * Provides the remote service utility for Item. This utility wraps
 * {@link net.indaba.lostandfound.service.impl.ItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author aritz
 * @see ItemService
 * @see net.indaba.lostandfound.service.base.ItemServiceBaseImpl
 * @see net.indaba.lostandfound.service.impl.ItemServiceImpl
 * @generated
 */
@ProviderType
public class ItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link net.indaba.lostandfound.service.impl.ItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static net.indaba.lostandfound.model.Item addItem(
		java.lang.String name) {
		return getService().addItem(name);
	}

	public static net.indaba.lostandfound.model.Item addItemRemote(
		java.lang.String name) {
		return getService().addItemRemote(name);
	}

	public static net.indaba.lostandfound.model.Item getItem(long itemId) {
		return getService().getItem(itemId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static net.indaba.lostandfound.model.Item removeItem(long itemId) {
		return getService().removeItem(itemId);
	}

	public static net.indaba.lostandfound.model.Item removeItemRemote(
		long itemId) {
		return getService().removeItemRemote(itemId);
	}

	public static java.lang.String test(java.lang.String in) {
		return getService().test(in);
	}

	public static net.indaba.lostandfound.model.Item updateItem(long itemId,
		java.lang.String name) {
		return getService().updateItem(itemId, name);
	}

	public static net.indaba.lostandfound.model.Item updateItemRemote(
		long itemId, java.lang.String name) {
		return getService().updateItemRemote(itemId, name);
	}

	public static void clearService() {
		_service = null;
	}

	public static ItemService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ItemService.class.getName());

			if (invokableService instanceof ItemService) {
				_service = (ItemService)invokableService;
			}
			else {
				_service = new ItemServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(ItemServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static ItemService _service;
}