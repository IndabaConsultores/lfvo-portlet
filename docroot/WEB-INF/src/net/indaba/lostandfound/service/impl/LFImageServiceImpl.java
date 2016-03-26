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

import com.liferay.portal.kernel.exception.PortalException;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.LFImageLocalServiceUtil;
import net.indaba.lostandfound.service.base.LFImageServiceBaseImpl;

/**
 * The implementation of the l f image remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link net.indaba.lostandfound.service.LFImageService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author aritz
 * @see LFImageServiceBaseImpl
 * @see net.indaba.lostandfound.service.LFImageServiceUtil
 */
@ProviderType
public class LFImageServiceImpl extends LFImageServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link net.indaba.lostandfound.service.LFImageServiceUtil} to access the l f image remote service.
	 */
	public List<LFImage> findByItemId(long itemId){
		return lfImagePersistence.findByItemId(itemId);
	}
	
	public LFImage addLFImage(LFImage lfImage, boolean updateFirebase) {
		return LFImageLocalServiceUtil.addLFImage(lfImage, updateFirebase);
	}
	
	public LFImage deleteLFImage(LFImage lfImage, boolean updateFirebase) {
		return LFImageLocalServiceUtil.deleteLFImage(lfImage, updateFirebase);

	}
	
	public LFImage addLFImage(LFImage lfImage) {
		return LFImageLocalServiceUtil.addLFImage(lfImage);
	}
	
	public LFImage deleteLFImage(LFImage lfImage) {
		return LFImageLocalServiceUtil.deleteLFImage(lfImage);
	}
	
	public LFImage deleteLFImage(long lfImageId) throws PortalException {
		return LFImageLocalServiceUtil.deleteLFImage(lfImageId);
	}
	
	public void deleteByItemId(long itemId){
		LFImageLocalServiceUtil.deleteByItemId(itemId);
	}
	
}