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

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.firebase.FirebaseLFImageSyncUtil;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.base.LFImageLocalServiceBaseImpl;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

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
	
	FirebaseLFImageSyncUtil firebaseUtil = FirebaseLFImageSyncUtil.getInstance();
	
	public List<LFImage> findByItemId(long itemId){
		return lfImagePersistence.findByItemId(itemId);
	}
	
	public LFImage addLFImage(LFImage lfImage, boolean updateFirebase) {
		/* supermethod needs to be called first, otherwise it somehow
		 * does not store the image blob into the database */
		LFImage image = super.addLFImage(lfImage);
		if (firebaseUtil.isSyncEnabled() && updateFirebase) {
			try {
				firebaseUtil.add(image);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException | PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return image;
	}
	
	public LFImage deleteLFImage(LFImage lfImage, boolean updateFirebase) {
		if (firebaseUtil.isSyncEnabled() && updateFirebase) {
			try {
				firebaseUtil.delete(lfImage);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException | PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.deleteLFImage(lfImage);
	}
	
	public LFImage deleteLFImage(long lfImageId, boolean updateFirebase) throws PortalException {
		return deleteLFImage(getLFImage(lfImageId), updateFirebase);
	}

	@Override
	public LFImage addLFImage(LFImage lfImage) {
		return addLFImage(lfImage, true);
	}
	
	@Override
	public LFImage deleteLFImage(LFImage lfImage) {
		return deleteLFImage(lfImage, true);
	}
	
	@Override
	public LFImage deleteLFImage(long lfImageId) throws PortalException {
		return deleteLFImage(lfImageId, true);
	}
	
	public void deleteByItemId(long itemId) {
		deleteByItemId(itemId, true);
	}
	
	public void deleteByItemId(long itemId, boolean updateFirebase){
		if (firebaseUtil.isSyncEnabled() && updateFirebase) {
			List<LFImage> images = lfImageLocalService.findByItemId(itemId);
			for (LFImage i : images) {
				deleteLFImage(i);
			}
		} else {
			lfImagePersistence.removeByItemId(itemId);
		}
	}
	
}