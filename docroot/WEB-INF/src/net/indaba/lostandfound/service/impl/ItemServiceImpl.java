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
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.liferay.portal.exception.NoSuchModelException;

import aQute.bnd.annotation.ProviderType;
import net.indaba.lostandfound.exception.NoSuchItemException;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.base.ItemServiceBaseImpl;

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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link
	 * net.indaba.lostandfound.service.ItemServiceUtil} to access the item
	 * remote service.
	 */

	public String test(String in) {
		return in;
	}

	public Item getItem(long itemId) {
		Item item = itemPersistence.fetchByPrimaryKey(itemId);
		return item;
	}

	public Item addItem(String name) {
		long itemId = counterLocalService.increment();
		Item item = itemPersistence.create(itemId);
		item.setName(name);

		itemPersistence.update(item);

		return item;
	}

	public Item updateItem(long itemId, String name) {
		Item item;
		item = itemPersistence.fetchByPrimaryKey(itemId);
		item.setName(name);
		itemPersistence.update(item);
		return item;

	}

	public Item removeItem(long itemId) {
		Item item;
		try {
			item = itemPersistence.remove(itemId);
			return item;
		} catch (NoSuchItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void firebaseRequest(Item item, String method) {
		URL url;
		try {
			url = new URL(FB_URI + "/items.json");

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.setDoInput(true);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

			// parse Item to JSON notation
			String data = "{\"id\":\"" + item.getItemId() + "\", \"name\":\"" + item.getName() + "\"}";

			wr.writeBytes(data);
			wr.close();

			// Get Response

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// not
															// Java 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
			System.out.println(response);
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}