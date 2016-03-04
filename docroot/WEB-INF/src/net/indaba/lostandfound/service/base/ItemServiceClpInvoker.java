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
		_methodName40 = "getOSGiServiceIdentifier";

		_methodParameterTypes40 = new String[] {  };

		_methodName45 = "addOrUpdateItem";

		_methodParameterTypes45 = new String[] {
				"net.indaba.lostandfound.model.Item",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName46 = "addOrUpdateItem";

		_methodParameterTypes46 = new String[] {
				"net.indaba.lostandfound.model.Item",
				"com.liferay.portal.kernel.service.ServiceContext", "boolean"
			};

		_methodName47 = "deleteItem";

		_methodParameterTypes47 = new String[] { "long", "boolean" };

		_methodName48 = "deleteItem";

		_methodParameterTypes48 = new String[] { "long" };

		_methodName49 = "deleteItem";

		_methodParameterTypes49 = new String[] {
				"net.indaba.lostandfound.model.Item", "boolean"
			};

		_methodName50 = "deleteItem";

		_methodParameterTypes50 = new String[] {
				"net.indaba.lostandfound.model.Item"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return ItemServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			return ItemServiceUtil.addOrUpdateItem((net.indaba.lostandfound.model.Item)arguments[0],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[1]);
		}

		if (_methodName46.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes46, parameterTypes)) {
			return ItemServiceUtil.addOrUpdateItem((net.indaba.lostandfound.model.Item)arguments[0],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[1],
				((Boolean)arguments[2]).booleanValue());
		}

		if (_methodName47.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes47, parameterTypes)) {
			return ItemServiceUtil.deleteItem(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName48.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes48, parameterTypes)) {
			return ItemServiceUtil.deleteItem(((Long)arguments[0]).longValue());
		}

		if (_methodName49.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes49, parameterTypes)) {
			return ItemServiceUtil.deleteItem((net.indaba.lostandfound.model.Item)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName50.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes50, parameterTypes)) {
			return ItemServiceUtil.deleteItem((net.indaba.lostandfound.model.Item)arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName45;
	private String[] _methodParameterTypes45;
	private String _methodName46;
	private String[] _methodParameterTypes46;
	private String _methodName47;
	private String[] _methodParameterTypes47;
	private String _methodName48;
	private String[] _methodParameterTypes48;
	private String _methodName49;
	private String[] _methodParameterTypes49;
	private String _methodName50;
	private String[] _methodParameterTypes50;
}