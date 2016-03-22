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

import java.util.List;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.base.LFImageLocalServiceBaseImpl;

/**
 * The implementation of the l f image local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link net.indaba.lostandfound.service.LFImageLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author aritz
 * @see LFImageLocalServiceBaseImpl
 * @see net.indaba.lostandfound.service.LFImageLocalServiceUtil
 */
@ProviderType
public class LFImageLocalServiceImpl extends LFImageLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link net.indaba.lostandfound.service.LFImageLocalServiceUtil} to access the l f image local service.
	 */
	
	public List<LFImage> findByItemId(long itemId){
		return lfImagePersistence.findByItemId(itemId);
	}
	
	public void deleteByItemId(long itemId){
		lfImagePersistence.removeByItemId(itemId);
	}
	
}