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

package net.indaba.lostandfound.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import net.indaba.lostandfound.exception.NoSuchLFImageException;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.model.impl.LFImageImpl;
import net.indaba.lostandfound.model.impl.LFImageModelImpl;
import net.indaba.lostandfound.service.persistence.LFImagePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the l f image service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author aritz
 * @see LFImagePersistence
 * @see net.indaba.lostandfound.service.persistence.LFImageUtil
 * @generated
 */
@ProviderType
public class LFImagePersistenceImpl extends BasePersistenceImpl<LFImage>
	implements LFImagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LFImageUtil} to access the l f image persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LFImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, LFImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, LFImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, LFImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, LFImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LFImageModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the l f images where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching l f images
	 */
	@Override
	public List<LFImage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the l f images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @return the range of matching l f images
	 */
	@Override
	public List<LFImage> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the l f images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching l f images
	 */
	@Override
	public List<LFImage> findByUuid(String uuid, int start, int end,
		OrderByComparator<LFImage> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the l f images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching l f images
	 */
	@Override
	public List<LFImage> findByUuid(String uuid, int start, int end,
		OrderByComparator<LFImage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<LFImage> list = null;

		if (retrieveFromCache) {
			list = (List<LFImage>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (LFImage lfImage : list) {
					if (!Validator.equals(uuid, lfImage.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LFIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LFImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<LFImage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LFImage>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first l f image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching l f image
	 * @throws NoSuchLFImageException if a matching l f image could not be found
	 */
	@Override
	public LFImage findByUuid_First(String uuid,
		OrderByComparator<LFImage> orderByComparator)
		throws NoSuchLFImageException {
		LFImage lfImage = fetchByUuid_First(uuid, orderByComparator);

		if (lfImage != null) {
			return lfImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLFImageException(msg.toString());
	}

	/**
	 * Returns the first l f image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching l f image, or <code>null</code> if a matching l f image could not be found
	 */
	@Override
	public LFImage fetchByUuid_First(String uuid,
		OrderByComparator<LFImage> orderByComparator) {
		List<LFImage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last l f image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching l f image
	 * @throws NoSuchLFImageException if a matching l f image could not be found
	 */
	@Override
	public LFImage findByUuid_Last(String uuid,
		OrderByComparator<LFImage> orderByComparator)
		throws NoSuchLFImageException {
		LFImage lfImage = fetchByUuid_Last(uuid, orderByComparator);

		if (lfImage != null) {
			return lfImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLFImageException(msg.toString());
	}

	/**
	 * Returns the last l f image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching l f image, or <code>null</code> if a matching l f image could not be found
	 */
	@Override
	public LFImage fetchByUuid_Last(String uuid,
		OrderByComparator<LFImage> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LFImage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the l f images before and after the current l f image in the ordered set where uuid = &#63;.
	 *
	 * @param lfImageId the primary key of the current l f image
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next l f image
	 * @throws NoSuchLFImageException if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage[] findByUuid_PrevAndNext(long lfImageId, String uuid,
		OrderByComparator<LFImage> orderByComparator)
		throws NoSuchLFImageException {
		LFImage lfImage = findByPrimaryKey(lfImageId);

		Session session = null;

		try {
			session = openSession();

			LFImage[] array = new LFImageImpl[3];

			array[0] = getByUuid_PrevAndNext(session, lfImage, uuid,
					orderByComparator, true);

			array[1] = lfImage;

			array[2] = getByUuid_PrevAndNext(session, lfImage, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LFImage getByUuid_PrevAndNext(Session session, LFImage lfImage,
		String uuid, OrderByComparator<LFImage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LFIMAGE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LFImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lfImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LFImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the l f images where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LFImage lfImage : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(lfImage);
		}
	}

	/**
	 * Returns the number of l f images where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching l f images
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LFIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "lfImage.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "lfImage.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(lfImage.uuid IS NULL OR lfImage.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, LFImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByItemId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID =
		new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, LFImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByItemId",
			new String[] { Long.class.getName() },
			LFImageModelImpl.ITEMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ITEMID = new FinderPath(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByItemId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the l f images where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the matching l f images
	 */
	@Override
	public List<LFImage> findByItemId(long itemId) {
		return findByItemId(itemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the l f images where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @return the range of matching l f images
	 */
	@Override
	public List<LFImage> findByItemId(long itemId, int start, int end) {
		return findByItemId(itemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the l f images where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching l f images
	 */
	@Override
	public List<LFImage> findByItemId(long itemId, int start, int end,
		OrderByComparator<LFImage> orderByComparator) {
		return findByItemId(itemId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the l f images where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching l f images
	 */
	@Override
	public List<LFImage> findByItemId(long itemId, int start, int end,
		OrderByComparator<LFImage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId, start, end, orderByComparator };
		}

		List<LFImage> list = null;

		if (retrieveFromCache) {
			list = (List<LFImage>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (LFImage lfImage : list) {
					if ((itemId != lfImage.getItemId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LFIMAGE_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LFImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				if (!pagination) {
					list = (List<LFImage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LFImage>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first l f image in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching l f image
	 * @throws NoSuchLFImageException if a matching l f image could not be found
	 */
	@Override
	public LFImage findByItemId_First(long itemId,
		OrderByComparator<LFImage> orderByComparator)
		throws NoSuchLFImageException {
		LFImage lfImage = fetchByItemId_First(itemId, orderByComparator);

		if (lfImage != null) {
			return lfImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("itemId=");
		msg.append(itemId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLFImageException(msg.toString());
	}

	/**
	 * Returns the first l f image in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching l f image, or <code>null</code> if a matching l f image could not be found
	 */
	@Override
	public LFImage fetchByItemId_First(long itemId,
		OrderByComparator<LFImage> orderByComparator) {
		List<LFImage> list = findByItemId(itemId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last l f image in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching l f image
	 * @throws NoSuchLFImageException if a matching l f image could not be found
	 */
	@Override
	public LFImage findByItemId_Last(long itemId,
		OrderByComparator<LFImage> orderByComparator)
		throws NoSuchLFImageException {
		LFImage lfImage = fetchByItemId_Last(itemId, orderByComparator);

		if (lfImage != null) {
			return lfImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("itemId=");
		msg.append(itemId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLFImageException(msg.toString());
	}

	/**
	 * Returns the last l f image in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching l f image, or <code>null</code> if a matching l f image could not be found
	 */
	@Override
	public LFImage fetchByItemId_Last(long itemId,
		OrderByComparator<LFImage> orderByComparator) {
		int count = countByItemId(itemId);

		if (count == 0) {
			return null;
		}

		List<LFImage> list = findByItemId(itemId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the l f images before and after the current l f image in the ordered set where itemId = &#63;.
	 *
	 * @param lfImageId the primary key of the current l f image
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next l f image
	 * @throws NoSuchLFImageException if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage[] findByItemId_PrevAndNext(long lfImageId, long itemId,
		OrderByComparator<LFImage> orderByComparator)
		throws NoSuchLFImageException {
		LFImage lfImage = findByPrimaryKey(lfImageId);

		Session session = null;

		try {
			session = openSession();

			LFImage[] array = new LFImageImpl[3];

			array[0] = getByItemId_PrevAndNext(session, lfImage, itemId,
					orderByComparator, true);

			array[1] = lfImage;

			array[2] = getByItemId_PrevAndNext(session, lfImage, itemId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LFImage getByItemId_PrevAndNext(Session session, LFImage lfImage,
		long itemId, OrderByComparator<LFImage> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LFIMAGE_WHERE);

		query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(LFImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(itemId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(lfImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LFImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the l f images where itemId = &#63; from the database.
	 *
	 * @param itemId the item ID
	 */
	@Override
	public void removeByItemId(long itemId) {
		for (LFImage lfImage : findByItemId(itemId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(lfImage);
		}
	}

	/**
	 * Returns the number of l f images where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the number of matching l f images
	 */
	@Override
	public int countByItemId(long itemId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ITEMID;

		Object[] finderArgs = new Object[] { itemId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LFIMAGE_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ITEMID_ITEMID_2 = "lfImage.itemId = ?";

	public LFImagePersistenceImpl() {
		setModelClass(LFImage.class);
	}

	/**
	 * Caches the l f image in the entity cache if it is enabled.
	 *
	 * @param lfImage the l f image
	 */
	@Override
	public void cacheResult(LFImage lfImage) {
		entityCache.putResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageImpl.class, lfImage.getPrimaryKey(), lfImage);

		lfImage.resetOriginalValues();
	}

	/**
	 * Caches the l f images in the entity cache if it is enabled.
	 *
	 * @param lfImages the l f images
	 */
	@Override
	public void cacheResult(List<LFImage> lfImages) {
		for (LFImage lfImage : lfImages) {
			if (entityCache.getResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
						LFImageImpl.class, lfImage.getPrimaryKey()) == null) {
				cacheResult(lfImage);
			}
			else {
				lfImage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all l f images.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LFImageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the l f image.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LFImage lfImage) {
		entityCache.removeResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageImpl.class, lfImage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LFImage> lfImages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LFImage lfImage : lfImages) {
			entityCache.removeResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
				LFImageImpl.class, lfImage.getPrimaryKey());
		}
	}

	/**
	 * Creates a new l f image with the primary key. Does not add the l f image to the database.
	 *
	 * @param lfImageId the primary key for the new l f image
	 * @return the new l f image
	 */
	@Override
	public LFImage create(long lfImageId) {
		LFImage lfImage = new LFImageImpl();

		lfImage.setNew(true);
		lfImage.setPrimaryKey(lfImageId);

		String uuid = PortalUUIDUtil.generate();

		lfImage.setUuid(uuid);

		return lfImage;
	}

	/**
	 * Removes the l f image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lfImageId the primary key of the l f image
	 * @return the l f image that was removed
	 * @throws NoSuchLFImageException if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage remove(long lfImageId) throws NoSuchLFImageException {
		return remove((Serializable)lfImageId);
	}

	/**
	 * Removes the l f image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the l f image
	 * @return the l f image that was removed
	 * @throws NoSuchLFImageException if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage remove(Serializable primaryKey)
		throws NoSuchLFImageException {
		Session session = null;

		try {
			session = openSession();

			LFImage lfImage = (LFImage)session.get(LFImageImpl.class, primaryKey);

			if (lfImage == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLFImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lfImage);
		}
		catch (NoSuchLFImageException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LFImage removeImpl(LFImage lfImage) {
		lfImage = toUnwrappedModel(lfImage);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lfImage)) {
				lfImage = (LFImage)session.get(LFImageImpl.class,
						lfImage.getPrimaryKeyObj());
			}

			if (lfImage != null) {
				session.delete(lfImage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lfImage != null) {
			clearCache(lfImage);
		}

		return lfImage;
	}

	@Override
	public LFImage updateImpl(LFImage lfImage) {
		lfImage = toUnwrappedModel(lfImage);

		boolean isNew = lfImage.isNew();

		LFImageModelImpl lfImageModelImpl = (LFImageModelImpl)lfImage;

		if (Validator.isNull(lfImage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			lfImage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (lfImage.isNew()) {
				session.save(lfImage);

				lfImage.setNew(false);
			}
			else {
				session.evict(lfImage);
				session.saveOrUpdate(lfImage);
			}

			session.flush();
			session.clear();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LFImageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((lfImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { lfImageModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { lfImageModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((lfImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lfImageModelImpl.getOriginalItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);

				args = new Object[] { lfImageModelImpl.getItemId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);
			}
		}

		entityCache.putResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
			LFImageImpl.class, lfImage.getPrimaryKey(), lfImage, false);

		lfImage.resetOriginalValues();

		return lfImage;
	}

	protected LFImage toUnwrappedModel(LFImage lfImage) {
		if (lfImage instanceof LFImageImpl) {
			return lfImage;
		}

		LFImageImpl lfImageImpl = new LFImageImpl();

		lfImageImpl.setNew(lfImage.isNew());
		lfImageImpl.setPrimaryKey(lfImage.getPrimaryKey());

		lfImageImpl.setUuid(lfImage.getUuid());
		lfImageImpl.setLfImageId(lfImage.getLfImageId());
		lfImageImpl.setItemId(lfImage.getItemId());
		lfImageImpl.setImage(lfImage.getImage());

		return lfImageImpl;
	}

	/**
	 * Returns the l f image with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the l f image
	 * @return the l f image
	 * @throws NoSuchLFImageException if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLFImageException {
		LFImage lfImage = fetchByPrimaryKey(primaryKey);

		if (lfImage == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLFImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return lfImage;
	}

	/**
	 * Returns the l f image with the primary key or throws a {@link NoSuchLFImageException} if it could not be found.
	 *
	 * @param lfImageId the primary key of the l f image
	 * @return the l f image
	 * @throws NoSuchLFImageException if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage findByPrimaryKey(long lfImageId)
		throws NoSuchLFImageException {
		return findByPrimaryKey((Serializable)lfImageId);
	}

	/**
	 * Returns the l f image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the l f image
	 * @return the l f image, or <code>null</code> if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage fetchByPrimaryKey(Serializable primaryKey) {
		LFImage lfImage = (LFImage)entityCache.getResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
				LFImageImpl.class, primaryKey);

		if (lfImage == _nullLFImage) {
			return null;
		}

		if (lfImage == null) {
			Session session = null;

			try {
				session = openSession();

				lfImage = (LFImage)session.get(LFImageImpl.class, primaryKey);

				if (lfImage != null) {
					cacheResult(lfImage);
				}
				else {
					entityCache.putResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
						LFImageImpl.class, primaryKey, _nullLFImage);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
					LFImageImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return lfImage;
	}

	/**
	 * Returns the l f image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lfImageId the primary key of the l f image
	 * @return the l f image, or <code>null</code> if a l f image with the primary key could not be found
	 */
	@Override
	public LFImage fetchByPrimaryKey(long lfImageId) {
		return fetchByPrimaryKey((Serializable)lfImageId);
	}

	@Override
	public Map<Serializable, LFImage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LFImage> map = new HashMap<Serializable, LFImage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LFImage lfImage = fetchByPrimaryKey(primaryKey);

			if (lfImage != null) {
				map.put(primaryKey, lfImage);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			LFImage lfImage = (LFImage)entityCache.getResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
					LFImageImpl.class, primaryKey);

			if (lfImage == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, lfImage);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LFIMAGE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (LFImage lfImage : (List<LFImage>)q.list()) {
				map.put(lfImage.getPrimaryKeyObj(), lfImage);

				cacheResult(lfImage);

				uncachedPrimaryKeys.remove(lfImage.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LFImageModelImpl.ENTITY_CACHE_ENABLED,
					LFImageImpl.class, primaryKey, _nullLFImage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the l f images.
	 *
	 * @return the l f images
	 */
	@Override
	public List<LFImage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the l f images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @return the range of l f images
	 */
	@Override
	public List<LFImage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the l f images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of l f images
	 */
	@Override
	public List<LFImage> findAll(int start, int end,
		OrderByComparator<LFImage> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the l f images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LFImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of l f images
	 * @param end the upper bound of the range of l f images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of l f images
	 */
	@Override
	public List<LFImage> findAll(int start, int end,
		OrderByComparator<LFImage> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LFImage> list = null;

		if (retrieveFromCache) {
			list = (List<LFImage>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LFIMAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LFIMAGE;

				if (pagination) {
					sql = sql.concat(LFImageModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LFImage>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LFImage>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the l f images from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LFImage lfImage : findAll()) {
			remove(lfImage);
		}
	}

	/**
	 * Returns the number of l f images.
	 *
	 * @return the number of l f images
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LFIMAGE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LFImageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the l f image persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LFImageImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_LFIMAGE = "SELECT lfImage FROM LFImage lfImage";
	private static final String _SQL_SELECT_LFIMAGE_WHERE_PKS_IN = "SELECT lfImage FROM LFImage lfImage WHERE lfImageId IN (";
	private static final String _SQL_SELECT_LFIMAGE_WHERE = "SELECT lfImage FROM LFImage lfImage WHERE ";
	private static final String _SQL_COUNT_LFIMAGE = "SELECT COUNT(lfImage) FROM LFImage lfImage";
	private static final String _SQL_COUNT_LFIMAGE_WHERE = "SELECT COUNT(lfImage) FROM LFImage lfImage WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lfImage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LFImage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LFImage exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LFImagePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final LFImage _nullLFImage = new LFImageImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LFImage> toCacheModel() {
				return _nullLFImageCacheModel;
			}
		};

	private static final CacheModel<LFImage> _nullLFImageCacheModel = new CacheModel<LFImage>() {
			@Override
			public LFImage toEntityModel() {
				return _nullLFImage;
			}
		};
}