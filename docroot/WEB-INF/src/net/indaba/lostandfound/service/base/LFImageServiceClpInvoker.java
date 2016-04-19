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

import net.indaba.lostandfound.service.LFImageServiceUtil;

import java.util.Arrays;

/**
 * @author aritz
 * @generated
 */
@ProviderType
public class LFImageServiceClpInvoker {
	public LFImageServiceClpInvoker() {
		_methodName30 = "getOSGiServiceIdentifier";

		_methodParameterTypes30 = new String[] {  };

		_methodName35 = "findByItemId";

		_methodParameterTypes35 = new String[] { "long" };

		_methodName36 = "addLFImage";

		_methodParameterTypes36 = new String[] {
				"net.indaba.lostandfound.model.LFImage", "boolean"
			};

		_methodName37 = "deleteLFImage";

		_methodParameterTypes37 = new String[] {
				"net.indaba.lostandfound.model.LFImage", "boolean"
			};

		_methodName38 = "addLFImage";

		_methodParameterTypes38 = new String[] {
				"net.indaba.lostandfound.model.LFImage"
			};

		_methodName39 = "deleteLFImage";

		_methodParameterTypes39 = new String[] {
				"net.indaba.lostandfound.model.LFImage"
			};

		_methodName40 = "deleteLFImage";

		_methodParameterTypes40 = new String[] { "long" };

		_methodName41 = "deleteByItemId";

		_methodParameterTypes41 = new String[] { "long" };

		_methodName42 = "addLFImage";

		_methodParameterTypes42 = new String[] { "java.lang.String", "long" };

		_methodName43 = "deleteLFImage";

		_methodParameterTypes43 = new String[] { "long", "boolean" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName30.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes30, parameterTypes)) {
			return LFImageServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName35.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes35, parameterTypes)) {
			return LFImageServiceUtil.findByItemId(((Long)arguments[0]).longValue());
		}

		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return LFImageServiceUtil.addLFImage((net.indaba.lostandfound.model.LFImage)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName37.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes37, parameterTypes)) {
			return LFImageServiceUtil.deleteLFImage((net.indaba.lostandfound.model.LFImage)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName38.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes38, parameterTypes)) {
			return LFImageServiceUtil.addLFImage((net.indaba.lostandfound.model.LFImage)arguments[0]);
		}

		if (_methodName39.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes39, parameterTypes)) {
			return LFImageServiceUtil.deleteLFImage((net.indaba.lostandfound.model.LFImage)arguments[0]);
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return LFImageServiceUtil.deleteLFImage(((Long)arguments[0]).longValue());
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			LFImageServiceUtil.deleteByItemId(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return LFImageServiceUtil.addLFImage((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return LFImageServiceUtil.deleteLFImage(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName30;
	private String[] _methodParameterTypes30;
	private String _methodName35;
	private String[] _methodParameterTypes35;
	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName37;
	private String[] _methodParameterTypes37;
	private String _methodName38;
	private String[] _methodParameterTypes38;
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
}