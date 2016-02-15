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

package net.indaba.lostandfound.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.service.http.TunnelUtil;

import net.indaba.lostandfound.service.ItemServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ItemServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author aritz
 * @see ItemServiceSoap
 * @see HttpPrincipal
 * @see ItemServiceUtil
 * @generated
 */
@ProviderType
public class ItemServiceHttp {
	public static java.lang.String test(HttpPrincipal httpPrincipal,
		java.lang.String in) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class, "test",
					_testParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, in);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item getItem(
		HttpPrincipal httpPrincipal, long itemId) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"getItem", _getItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item addItem(
		HttpPrincipal httpPrincipal, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"addItem", _addItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item updateItem(
		HttpPrincipal httpPrincipal, long itemId, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"updateItem", _updateItemParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId,
					name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item removeItem(
		HttpPrincipal httpPrincipal, long itemId) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"removeItem", _removeItemParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item addItemRemote(
		HttpPrincipal httpPrincipal, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"addItemRemote", _addItemRemoteParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item updateItemRemote(
		HttpPrincipal httpPrincipal, long itemId, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"updateItemRemote", _updateItemRemoteParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId,
					name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.Item removeItemRemote(
		HttpPrincipal httpPrincipal, long itemId) {
		try {
			MethodKey methodKey = new MethodKey(ItemServiceUtil.class,
					"removeItemRemote", _removeItemRemoteParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.Item)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ItemServiceHttp.class);
	private static final Class<?>[] _testParameterTypes0 = new Class[] {
			java.lang.String.class
		};
	private static final Class<?>[] _getItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _addItemParameterTypes2 = new Class[] {
			java.lang.String.class
		};
	private static final Class<?>[] _updateItemParameterTypes3 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _removeItemParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _addItemRemoteParameterTypes5 = new Class[] {
			java.lang.String.class
		};
	private static final Class<?>[] _updateItemRemoteParameterTypes6 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _removeItemRemoteParameterTypes7 = new Class[] {
			long.class
		};
}