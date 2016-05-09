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
import java.util.concurrent.Future;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.firebase.FirebaseService;
import net.indaba.lostandfound.firebase.FirebaseSynchronizer;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.service.base.LFImageLocalServiceBaseImpl;

/**
 * The implementation of the l f image local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link net.indaba.lostandfound.service.LFImageLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
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
	 * Never reference this class directly. Always use {@link
	 * net.indaba.lostandfound.service.LFImageLocalServiceUtil} to access the l
	 * f image local service.
	 */

	FirebaseService<LFImage> firebaseUtil = FirebaseSynchronizer
			.getInstance().getService(LFImage.class);

	private boolean updateFirebase(LFImage image,
			ServiceContext serviceContext) {
		ThemeDisplay themeDisplay = new ThemeDisplay();
		if (serviceContext != null) {
			themeDisplay = (ThemeDisplay) serviceContext.getRequest()
					.getAttribute(WebKeys.THEME_DISPLAY);
		}
		return (firebaseUtil.isSyncEnabled() && themeDisplay != null);
	}

	public List<LFImage> findByItemId(long itemId) {
		return lfImagePersistence.findByItemId(itemId);
	}

	public LFImage addLFImage(LFImage lfImage, ServiceContext serviceContext) {
		/*
		 * supermethod needs to be called first, otherwise it somehow does not
		 * store the image blob into the database
		 */
		LFImage image = super.addLFImage(lfImage);
		if (updateFirebase(lfImage, serviceContext)) {
			Future<String> fbKey = firebaseUtil.add(image, null);
			Item item = ItemLocalServiceUtil.fetchItem(lfImage.getItemId());
			FirebaseService<Item> fbItemService = FirebaseSynchronizer
					.getInstance().getService(Item.class);
			firebaseUtil.setRelationManyToOne(lfImage, item, fbItemService,
					fbKey);
		}
		return image;
	}

	public LFImage deleteLFImage(LFImage lfImage,
			ServiceContext serviceContext) {
		if (updateFirebase(lfImage, serviceContext)) {
			Item item = ItemLocalServiceUtil.fetchItem(lfImage.getItemId());
			FirebaseService<Item> fbItemService = FirebaseSynchronizer
					.getInstance().getService(Item.class);
			Future<Boolean> result = firebaseUtil.setRelationManyToOne(lfImage,
					item, fbItemService, null);
			firebaseUtil.delete(lfImage, result);
		}
		return super.deleteLFImage(lfImage);
	}

	public LFImage deleteLFImage(long lfImageId, ServiceContext serviceContext)
			throws PortalException {
		return deleteLFImage(getLFImage(lfImageId), serviceContext);
	}

	public void deleteByItemId(long itemId, ServiceContext serviceContext) {
		if (updateFirebase(null, serviceContext)) {
			List<LFImage> images = lfImageLocalService.findByItemId(itemId);
			for (LFImage i : images) {
				deleteLFImage(i, serviceContext);
			}
		} else {
			lfImagePersistence.removeByItemId(itemId);
		}
	}

}