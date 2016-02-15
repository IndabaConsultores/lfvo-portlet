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

		_methodName39 = "test";

		_methodParameterTypes39 = new String[] { "java.lang.String" };

		_methodName40 = "getItem";

		_methodParameterTypes40 = new String[] { "long" };

		_methodName41 = "addItem";

		_methodParameterTypes41 = new String[] { "java.lang.String" };

		_methodName42 = "updateItem";

		_methodParameterTypes42 = new String[] { "long", "java.lang.String" };

		_methodName43 = "removeItem";

		_methodParameterTypes43 = new String[] { "long" };

		_methodName44 = "addItemRemote";

		_methodParameterTypes44 = new String[] { "java.lang.String" };

		_methodName45 = "updateItemRemote";

		_methodParameterTypes45 = new String[] { "long", "java.lang.String" };

		_methodName46 = "removeItemRemote";

		_methodParameterTypes46 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName34.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes34, parameterTypes)) {
			return ItemServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName39.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes39, parameterTypes)) {
			return ItemServiceUtil.test((java.lang.String)arguments[0]);
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return ItemServiceUtil.getItem(((Long)arguments[0]).longValue());
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return ItemServiceUtil.addItem((java.lang.String)arguments[0]);
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return ItemServiceUtil.updateItem(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return ItemServiceUtil.removeItem(((Long)arguments[0]).longValue());
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return ItemServiceUtil.addItemRemote((java.lang.String)arguments[0]);
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			return ItemServiceUtil.updateItemRemote(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName46.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes46, parameterTypes)) {
			return ItemServiceUtil.removeItemRemote(((Long)arguments[0]).longValue());
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
	private String _methodName45;
	private String[] _methodParameterTypes45;
	private String _methodName46;
	private String[] _methodParameterTypes46;
}