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

import net.indaba.lostandfound.service.LFImageServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link LFImageServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link net.indaba.lostandfound.model.LFImageSoap}.
 * If the method in the service utility returns a
 * {@link net.indaba.lostandfound.model.LFImage}, that is translated to a
 * {@link net.indaba.lostandfound.model.LFImageSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author aritz
 * @see LFImageServiceHttp
 * @see net.indaba.lostandfound.model.LFImageSoap
 * @see LFImageServiceUtil
 * @generated
 */
@ProviderType
public class LFImageServiceSoap {
	public static net.indaba.lostandfound.model.LFImageSoap[] findByItemId(
		long itemId) throws RemoteException {
		try {
			java.util.List<net.indaba.lostandfound.model.LFImage> returnValue = LFImageServiceUtil.findByItemId(itemId);

			return net.indaba.lostandfound.model.LFImageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static net.indaba.lostandfound.model.LFImageSoap addLFImage(
		net.indaba.lostandfound.model.LFImageSoap lfImage,
		boolean updateFirebase) throws RemoteException {
		try {
			net.indaba.lostandfound.model.LFImage returnValue = LFImageServiceUtil.addLFImage(net.indaba.lostandfound.model.impl.LFImageModelImpl.toModel(
						lfImage), updateFirebase);

			return net.indaba.lostandfound.model.LFImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static net.indaba.lostandfound.model.LFImageSoap deleteLFImage(
		net.indaba.lostandfound.model.LFImageSoap lfImage,
		boolean updateFirebase) throws RemoteException {
		try {
			net.indaba.lostandfound.model.LFImage returnValue = LFImageServiceUtil.deleteLFImage(net.indaba.lostandfound.model.impl.LFImageModelImpl.toModel(
						lfImage), updateFirebase);

			return net.indaba.lostandfound.model.LFImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static net.indaba.lostandfound.model.LFImageSoap addLFImage(
		net.indaba.lostandfound.model.LFImageSoap lfImage)
		throws RemoteException {
		try {
			net.indaba.lostandfound.model.LFImage returnValue = LFImageServiceUtil.addLFImage(net.indaba.lostandfound.model.impl.LFImageModelImpl.toModel(
						lfImage));

			return net.indaba.lostandfound.model.LFImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static net.indaba.lostandfound.model.LFImageSoap deleteLFImage(
		net.indaba.lostandfound.model.LFImageSoap lfImage)
		throws RemoteException {
		try {
			net.indaba.lostandfound.model.LFImage returnValue = LFImageServiceUtil.deleteLFImage(net.indaba.lostandfound.model.impl.LFImageModelImpl.toModel(
						lfImage));

			return net.indaba.lostandfound.model.LFImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static net.indaba.lostandfound.model.LFImageSoap deleteLFImage(
		long lfImageId) throws RemoteException {
		try {
			net.indaba.lostandfound.model.LFImage returnValue = LFImageServiceUtil.deleteLFImage(lfImageId);

			return net.indaba.lostandfound.model.LFImageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteByItemId(long itemId) throws RemoteException {
		try {
			LFImageServiceUtil.deleteByItemId(itemId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LFImageServiceSoap.class);
}