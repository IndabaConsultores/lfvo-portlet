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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.InvokableService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import net.indaba.lostandfound.model.Item;

/**
 * Provides the remote service interface for Item. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author aritz
 * @see ItemServiceUtil
 * @see net.indaba.lostandfound.service.base.ItemServiceBaseImpl
 * @see net.indaba.lostandfound.service.impl.ItemServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ItemService extends BaseService, InvokableService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ItemServiceUtil} to access the item remote service. Add custom service methods to {@link net.indaba.lostandfound.service.impl.ItemServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Item addOrUpdateItem(Item item, ServiceContext serviceContext)
		throws PortalException;

	public Item addOrUpdateItem(Item item, ServiceContext serviceContext,
		boolean updateFirebase) throws PortalException;

	public Item deleteItem(Item item) throws PortalException;

	public Item deleteItem(Item item, boolean updateFirebase)
		throws PortalException;

	public Item deleteItem(long itemId) throws PortalException;

	public Item deleteItem(long itemId, boolean updateFirebase)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;
}