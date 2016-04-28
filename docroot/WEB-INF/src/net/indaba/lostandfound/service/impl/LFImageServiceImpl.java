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

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

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
	
	public LFImage addLFImage(LFImage lfImage, ServiceContext serviceContext) {
		return LFImageLocalServiceUtil.addLFImage(lfImage, serviceContext);
	}
	
	public LFImage deleteLFImage(LFImage lfImage, ServiceContext serviceContext) {
		return LFImageLocalServiceUtil.deleteLFImage(lfImage, serviceContext);
	}
	
	public LFImage deleteLFImage(long lfImageId, ServiceContext serviceContext) throws PortalException {
		return LFImageLocalServiceUtil.deleteLFImage(lfImageId, serviceContext);
	}
	
	public void deleteByItemId(long itemId, ServiceContext serviceContext){
		LFImageLocalServiceUtil.deleteByItemId(itemId, serviceContext);
	}
	
	public LFImage addLFImage(String imageBase64String, long itemId, ServiceContext serviceContext) {
		ByteArrayInputStream imageBase64 = new ByteArrayInputStream(imageBase64String.getBytes(StandardCharsets.UTF_8));
		OutputBlob dataOutputBlob = new OutputBlob(imageBase64, imageBase64String.length());
		
		LFImage lfImage = LFImageLocalServiceUtil.createLFImage(CounterLocalServiceUtil.increment());
		lfImage.setItemId(itemId);
		lfImage.setImage(dataOutputBlob);
		return LFImageLocalServiceUtil.addLFImage(lfImage, serviceContext);
	}
	
}