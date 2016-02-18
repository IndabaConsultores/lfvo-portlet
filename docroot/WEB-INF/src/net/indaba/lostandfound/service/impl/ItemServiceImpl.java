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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.liferay.portal.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.exception.NoSuchItemException;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.service.base.ItemServiceBaseImpl;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 * The implementation of the item remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link net.indaba.lostandfound.service.ItemService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have
 * security checks based on the propagated JAAS credentials because this service
 * can be accessed remotely.
 * </p>
 *
 * @author aritz
 * @see ItemServiceBaseImpl
 * @see net.indaba.lostandfound.service.ItemServiceUtil
 */
@ProviderType
public class ItemServiceImpl extends ItemServiceBaseImpl {

	private final String FB_URI = "https://brilliant-torch-8285.firebaseio.com";

	
	public Item addOrUpdateItem(Item item, ServiceContext serviceContext) throws PortalException {
		return ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext);
	}
	
	public Item addOrUpdateItem(Item item, ServiceContext serviceContext, boolean updateFirebase) throws PortalException {
		return ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext, updateFirebase);
	}
	
	public Item deleteItem(long itemId, boolean updateFirebase) throws PortalException {
		return ItemLocalServiceUtil.deleteItem(itemId, updateFirebase);
	}

	public Item deleteItem(long itemId) throws PortalException {
		return ItemLocalServiceUtil.deleteItem(itemId);
	}

	public Item deleteItem(Item item, boolean updateFirebase) throws PortalException {
		return ItemLocalServiceUtil.deleteItem(item, updateFirebase);
	}

	public Item deleteItem(Item item) throws PortalException {
		return ItemLocalServiceUtil.deleteItem(item);
	}
	
}