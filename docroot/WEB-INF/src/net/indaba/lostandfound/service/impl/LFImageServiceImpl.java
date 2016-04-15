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
	
	public LFImage addLFImage(String imageBase64String, long itemId) {
		ByteArrayInputStream imageBase64 = new ByteArrayInputStream(imageBase64String.getBytes(StandardCharsets.UTF_8));
		OutputBlob dataOutputBlob = new OutputBlob(imageBase64, imageBase64String.length());
		
		LFImage lfImage = LFImageLocalServiceUtil.createLFImage(CounterLocalServiceUtil.increment());
		lfImage.setItemId(itemId);
		lfImage.setImage(dataOutputBlob);
		return LFImageLocalServiceUtil.addLFImage(lfImage, false);
	}
	
	public LFImage deleteLFImage(long lfImageId, boolean updateFirebase) {
		// TODO solve HibernateException
		/* When calling this method it somehow throws an exception 
		 * LocalService does not throw said exception, so the problem must lie 
		 * in this service layer. Might have something to do with the Blob field */
		try {
			return LFImageLocalServiceUtil.deleteLFImage(lfImageId, updateFirebase);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}
	
}