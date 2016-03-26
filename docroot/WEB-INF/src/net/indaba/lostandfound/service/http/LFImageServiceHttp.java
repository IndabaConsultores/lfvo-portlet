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
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import net.indaba.lostandfound.service.LFImageServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link LFImageServiceUtil} service utility. The
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
 * @see LFImageServiceSoap
 * @see HttpPrincipal
 * @see LFImageServiceUtil
 * @generated
 */
@ProviderType
public class LFImageServiceHttp {
	public static java.util.List<net.indaba.lostandfound.model.LFImage> findByItemId(
		HttpPrincipal httpPrincipal, long itemId) {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"findByItemId", _findByItemIdParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<net.indaba.lostandfound.model.LFImage>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.LFImage addLFImage(
		HttpPrincipal httpPrincipal,
		net.indaba.lostandfound.model.LFImage lfImage, boolean updateFirebase) {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"addLFImage", _addLFImageParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, lfImage,
					updateFirebase);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.LFImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.LFImage deleteLFImage(
		HttpPrincipal httpPrincipal,
		net.indaba.lostandfound.model.LFImage lfImage, boolean updateFirebase) {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"deleteLFImage", _deleteLFImageParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, lfImage,
					updateFirebase);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.LFImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.LFImage addLFImage(
		HttpPrincipal httpPrincipal,
		net.indaba.lostandfound.model.LFImage lfImage) {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"addLFImage", _addLFImageParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, lfImage);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.LFImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.LFImage deleteLFImage(
		HttpPrincipal httpPrincipal,
		net.indaba.lostandfound.model.LFImage lfImage) {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"deleteLFImage", _deleteLFImageParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, lfImage);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.LFImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static net.indaba.lostandfound.model.LFImage deleteLFImage(
		HttpPrincipal httpPrincipal, long lfImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"deleteLFImage", _deleteLFImageParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, lfImageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (net.indaba.lostandfound.model.LFImage)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteByItemId(HttpPrincipal httpPrincipal, long itemId) {
		try {
			MethodKey methodKey = new MethodKey(LFImageServiceUtil.class,
					"deleteByItemId", _deleteByItemIdParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LFImageServiceHttp.class);
	private static final Class<?>[] _findByItemIdParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _addLFImageParameterTypes1 = new Class[] {
			net.indaba.lostandfound.model.LFImage.class, boolean.class
		};
	private static final Class<?>[] _deleteLFImageParameterTypes2 = new Class[] {
			net.indaba.lostandfound.model.LFImage.class, boolean.class
		};
	private static final Class<?>[] _addLFImageParameterTypes3 = new Class[] {
			net.indaba.lostandfound.model.LFImage.class
		};
	private static final Class<?>[] _deleteLFImageParameterTypes4 = new Class[] {
			net.indaba.lostandfound.model.LFImage.class
		};
	private static final Class<?>[] _deleteLFImageParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteByItemIdParameterTypes6 = new Class[] {
			long.class
		};
}