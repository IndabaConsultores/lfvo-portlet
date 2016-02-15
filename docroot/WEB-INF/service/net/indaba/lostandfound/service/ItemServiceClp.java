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

package net.indaba.lostandfound.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.InvokableService;

/**
 * @author aritz
 * @generated
 */
@ProviderType
public class ItemServiceClp implements ItemService {
	public ItemServiceClp(InvokableService invokableService) {
		_invokableService = invokableService;

		_methodName0 = "addItem";

		_methodParameterTypes0 = new String[] { "java.lang.String" };

		_methodName1 = "addItemRemote";

		_methodParameterTypes1 = new String[] { "java.lang.String" };

		_methodName2 = "getItem";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "getOSGiServiceIdentifier";

		_methodParameterTypes3 = new String[] {  };

		_methodName5 = "removeItem";

		_methodParameterTypes5 = new String[] { "long" };

		_methodName6 = "removeItemRemote";

		_methodParameterTypes6 = new String[] { "long" };

		_methodName7 = "test";

		_methodParameterTypes7 = new String[] { "java.lang.String" };

		_methodName8 = "updateItem";

		_methodParameterTypes8 = new String[] { "long", "java.lang.String" };

		_methodName9 = "updateItemRemote";

		_methodParameterTypes9 = new String[] { "long", "java.lang.String" };
	}

	@Override
	public net.indaba.lostandfound.model.Item addItem(java.lang.String name) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName0,
					_methodParameterTypes0,
					new Object[] { ClpSerializer.translateInput(name) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public net.indaba.lostandfound.model.Item addItemRemote(
		java.lang.String name) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName1,
					_methodParameterTypes1,
					new Object[] { ClpSerializer.translateInput(name) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public net.indaba.lostandfound.model.Item getItem(long itemId) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName2,
					_methodParameterTypes2, new Object[] { itemId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName3,
					_methodParameterTypes3, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}

	@Override
	public net.indaba.lostandfound.model.Item removeItem(long itemId) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName5,
					_methodParameterTypes5, new Object[] { itemId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public net.indaba.lostandfound.model.Item removeItemRemote(long itemId) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName6,
					_methodParameterTypes6, new Object[] { itemId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public java.lang.String test(java.lang.String in) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName7,
					_methodParameterTypes7,
					new Object[] { ClpSerializer.translateInput(in) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public net.indaba.lostandfound.model.Item updateItem(long itemId,
		java.lang.String name) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName8,
					_methodParameterTypes8,
					new Object[] { itemId, ClpSerializer.translateInput(name) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public net.indaba.lostandfound.model.Item updateItemRemote(long itemId,
		java.lang.String name) {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName9,
					_methodParameterTypes9,
					new Object[] { itemId, ClpSerializer.translateInput(name) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (net.indaba.lostandfound.model.Item)ClpSerializer.translateOutput(returnObj);
	}

	private InvokableService _invokableService;
	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
}