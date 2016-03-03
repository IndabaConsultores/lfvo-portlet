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

package net.indaba.lostandfound.service.base;

import aQute.bnd.annotation.ProviderType;

import net.indaba.lostandfound.service.ItemServiceUtil;

import java.util.Arrays;

/**
 * @author aritz
 * @generated
 */
@ProviderType
public class ItemServiceClpInvoker {
	public ItemServiceClpInvoker() {
		_methodName34 = "getOSGiServiceIdentifier";

		_methodParameterTypes34 = new String[] {  };

		_methodName39 = "addOrUpdateItem";

		_methodParameterTypes39 = new String[] {
				"net.indaba.lostandfound.model.Item",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName40 = "addOrUpdateItem";

		_methodParameterTypes40 = new String[] {
				"net.indaba.lostandfound.model.Item",
				"com.liferay.portal.kernel.service.ServiceContext", "boolean"
			};

		_methodName41 = "deleteItem";

		_methodParameterTypes41 = new String[] { "long", "boolean" };

		_methodName42 = "deleteItem";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "deleteItem";

		_methodParameterTypes43 = new String[] {
				"net.indaba.lostandfound.model.Item", "boolean"
			};

		_methodName44 = "deleteItem";

		_methodParameterTypes44 = new String[] {
				"net.indaba.lostandfound.model.Item"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName34.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes34, parameterTypes)) {
			return ItemServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName39.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes39, parameterTypes)) {
			return ItemServiceUtil.addOrUpdateItem((net.indaba.lostandfound.model.Item)arguments[0],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[1]);
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return ItemServiceUtil.addOrUpdateItem((net.indaba.lostandfound.model.Item)arguments[0],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[1],
				((Boolean)arguments[2]).booleanValue());
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return ItemServiceUtil.deleteItem(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return ItemServiceUtil.deleteItem(((Long)arguments[0]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return ItemServiceUtil.deleteItem((net.indaba.lostandfound.model.Item)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return ItemServiceUtil.deleteItem((net.indaba.lostandfound.model.Item)arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName34;
	private String[] _methodParameterTypes34;
	private String _methodName39;
	private String[] _methodParameterTypes39;
	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
}