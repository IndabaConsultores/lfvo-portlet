package net.indaba.lostandfound.hook;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.util.portlet.PortletProps;

import net.indaba.lostandfound.firebase.FirebaseMapper;
import net.indaba.lostandfound.firebase.FirebaseService;
import net.indaba.lostandfound.firebase.FirebaseServiceFactory;
import net.indaba.lostandfound.firebase.FirebaseSynchronizer;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public class FirebaseServicesInitAction extends SimpleAction {

	private FirebaseMapper<Item> itemMapper = new FirebaseMapper<Item>() {

		public Map<String, Object> toMap(Item item) {
			HashMap<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getItemId());
			itemMap.put("name", item.getName());
			itemMap.put("description", item.getDescription());
			itemMap.put("createdAt", item.getCreateDate());
			itemMap.put("modifiedAt", item.getModifiedDate());
			itemMap.put("office", item.getGroupId());
			itemMap.put("companyId", item.getCompanyId());
			itemMap.put("type", item.getType());
			Map<String, Object> location = new HashMap<String, Object>();
			location.put("latitude", item.getLat());
			location.put("longitude", item.getLng());
			itemMap.put("location", location);
			itemMap.put("createdBy", PortletProps.get("firebase.user.id"));
			return itemMap;
		}

		public Item parseMap(Map<String, Object> itemMap) {
			Item item;
			Object o;
			o = itemMap.get("id");
			if (o != null) {
				item = ItemLocalServiceUtil
						.createItem(Long.valueOf(o.toString()));
				item.setNew(false);
			} else {
				item = ItemLocalServiceUtil.createItem(0);
				item.setNew(true);
			}
			o = itemMap.get("name");
			item.setName(o != null ? (String) o : "");
			o = itemMap.get("description");
			item.setDescription(o != null ? (String) o : "");
			o = itemMap.get("createdAt");
			item.setCreateDate(
					new Date(o != null ? Long.valueOf(o.toString()) : 0));
			o = itemMap.get("modifiedAt");
			item.setModifiedDate(
					new Date(o != null ? Long.valueOf(o.toString()) : 0));
			o = itemMap.get("office");
			item.setGroupId(o != null ? Long.valueOf(o.toString()) : 0);
			o = itemMap.get("companyId");
			item.setCompanyId(o != null ? Long.valueOf(o.toString()) : 0);
			o = itemMap.get("type");
			item.setType(o != null ? String.valueOf(o) : "office");
			o = itemMap.get("location");
			if (o != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> locationMap = (Map<String, Object>) o;
				o = locationMap.get("latitude");
				item.setLat(o != null ? Long.valueOf(o.toString()) : 0);
				o = locationMap.get("longitude");
				item.setLng(o != null ? Long.valueOf(o.toString()) : 0);
			}
			return item;
		}

	};

	private FirebaseService<Item> fbItemService = new FirebaseService<Item>(
			PortletProps.get("firebase.url"), "item", "items", itemMapper) {

		public String add(Item item) {
			try {
				String itemType = getItemType(item);
				Firebase firebase = new Firebase(getFbURI() + "/" + itemType);
				Map<String, Object> entityMap = getFbMapper().toMap(item);
				FirebaseResponse response = firebase.post(entityMap);
				if (response.getCode() == 200) {
					_log.debug("Firebase create sucessful");
					return (String) response.getBody().get("name");
				} else {
					_log.error("Firebase create unsuccessful. Response code: "
							+ response.getCode());
					return null;
				}
			} catch (FirebaseException | JacksonUtilityException
					| UnsupportedEncodingException e) {
				_log.error("Firebase create unsuccessful. Error : "
						+ e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		public String getFirebaseKey(Item item) {
			try {
				String itemType = getItemType(item);
				Firebase firebase = new Firebase(getFbURI() + "/" + itemType);

				firebase.addQuery("orderBy", getFbIdField());
				firebase.addQuery("equalTo",
						String.valueOf(item.getPrimaryKeyObj()));
				FirebaseResponse response = firebase.get();
				if (response.getCode() == 200) {
					Map<String, Object> responseMap = response.getBody();
					Object[] keys = responseMap.keySet().toArray();
					if (keys.length > 0) {
						return itemType + "/" + (String) keys[0];
					} else {
						return null;
					}
				} else {
					_log.error("Firebase get key unsuccessfull. Error: "
							+ response.getCode() + " "
							+ response.getBody().get("error"));
				}
			} catch (FirebaseException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}

		private String getItemType(Item item) {
			switch (item.getType()) {
			case "alert":
			case "lost":
			case "found":
				return "alert";
			default:
				return "office";
			}
		}

	};

	private FirebaseMapper<AssetCategory> catMapper = new FirebaseMapper<AssetCategory>() {

		public Map<String, Object> toMap(AssetCategory category) {
			Map<String, Object> categoryMap = new HashMap<String, Object>();
			categoryMap.put("title", category.getTitleMap());
			categoryMap.put("name", category.getName());
			categoryMap.put("description", category.getDescriptionMap());
			categoryMap.put("office", category.getGroupId());
			categoryMap.put("companyId", category.getCompanyId());
			categoryMap.put("createDate", category.getCreateDate());
			categoryMap.put("modifiedDate", category.getModifiedDate());
			return categoryMap;
		}

		public AssetCategory parseMap(Map<String, Object> entityMap) {
			// Never used
			return null;
		}

	};

	private FirebaseService<AssetCategory> fbCatService = FirebaseServiceFactory
			.createService(FirebaseServiceFactory.SYNC_TYPE.ONE_WAY, "category",
					"categories", catMapper);

	private FirebaseMapper<LFImage> imgMapper = new FirebaseMapper<LFImage>() {

		public Map<String, Object> toMap(LFImage image) {
			Map<String, Object> imageMap = image.getModelAttributes();
			imageMap.remove("lfImageId");
			imageMap.put("id", image.getPrimaryKey());
			try {
				Blob imageBlob = image.getImage();
				byte[] b = imageBlob.getBytes(1, (int) imageBlob.length());
				String imageString = "data:image/jpeg;base64,";
				imageString += new String(b);
				imageMap.replace("image", imageString);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return imageMap;
		}

		public LFImage parseMap(Map<String, Object> entityMap) {
			// TODO implement method body
			return null;
		}

	};

	private FirebaseService<LFImage> fbImageService = FirebaseServiceFactory
			.createService(FirebaseServiceFactory.SYNC_TYPE.TWO_WAY, "image",
					"images", imgMapper);

	private FirebaseMapper<Group> groupMapper = new FirebaseMapper<Group>() {

		public Map<String, Object> toMap(Group group) {
			Map<String, Object> groupMap = new HashMap<String, Object>();
			groupMap.put("name", group.getNameMap());
			groupMap.put("description", group.getDescriptionMap());
			groupMap.put("companyId", group.getCompanyId());
			return groupMap;
		};

		public Group parseMap(Map<String, Object> entityMap) {
			// TODO implement method body
			return null;
		}
	};

	private FirebaseService<Group> fbGroupService = FirebaseServiceFactory
			.createService(FirebaseServiceFactory.SYNC_TYPE.ONE_WAY, "office",
					"offices", groupMapper);

	private FirebaseMapper<MBMessage> msgMapper = new FirebaseMapper<MBMessage>() {

		public Map<String, Object> toMap(MBMessage message) {
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("id", message.getMessageId());
			messageMap.put("createDate", message.getCreateDate());
			messageMap.put("modifiedDate", message.getModifiedDate());
			messageMap.put("companyId", message.getCompanyId());
			messageMap.put("office", message.getGroupId());
			messageMap.put("itemId", message.getClassPK());
			messageMap.put("subject", message.getSubject());
			messageMap.put("body", message.getBody());
			return messageMap;
		};

		public MBMessage parseMap(Map<String, Object> map) {
			MBMessage msg;
			Object o = map.get("id");
			if (o == null) {
				msg = MBMessageLocalServiceUtil.createMBMessage(0);
				msg.setNew(true);
			} else {
				msg = MBMessageLocalServiceUtil
						.createMBMessage(Long.valueOf(o.toString()));
				msg.setNew(false);
			}
			o = map.get("office");
			if (o != null) {
				msg.setGroupId(Long.valueOf(o.toString()));
			}
			o = map.get("companyId");
			if (o != null) {
				msg.setCompanyId(Long.valueOf(o.toString()));
			}
			o = map.get("itemId");
			if (o != null) {
				msg.setClassPK(Long.valueOf(o.toString()));
			}
			o = map.get("subject");
			if (o != null) {
				msg.setSubject(o.toString());
			}
			o = map.get("body");
			if (o != null) {
				msg.setBody(o.toString());
			}
			o = map.get("createDate");
			if (o != null) {
				msg.setCreateDate(new Date(Long.valueOf(o.toString())));
			}
			o = map.get("modifiedDate");
			if (o != null) {
				msg.setModifiedDate(new Date(Long.valueOf(o.toString())));
			}
			msg.setClassName(Item.class.getName());
			return msg;
		};

	};

	private FirebaseMapper<AssetTag> tagMapper = new FirebaseMapper<AssetTag>() {

		public Map<String, Object> toMap(AssetTag entity) {
			return entity.getModelAttributes();
		}

		public AssetTag parseMap(Map<String, Object> entityMap) {
			AssetTag tag = AssetTagLocalServiceUtil.createAssetTag(Long.valueOf(
					entityMap.get("id").toString()));
			tag.setModelAttributes(entityMap);
			return null;
		}

	};

	private FirebaseService<AssetTag> fbTagService = FirebaseServiceFactory
			.createService(FirebaseServiceFactory.SYNC_TYPE.ONE_WAY, "tag",
					"tags", tagMapper);

	private FirebaseService<MBMessage> fbMsgService = FirebaseServiceFactory
			.createService(FirebaseServiceFactory.SYNC_TYPE.TWO_WAY, "message",
					"messages", msgMapper);

	@Override
	public void run(String[] arg0) throws ActionException {
		FirebaseSynchronizer fbs = FirebaseSynchronizer.getInstance();
		fbs.addService(Item.class, fbItemService);
		fbs.addService(LFImage.class, fbImageService);
		fbs.addService(AssetCategory.class, fbCatService);
		fbs.addService(Group.class, fbGroupService);
		fbs.addService(MBMessage.class, fbMsgService);
		fbs.addService(AssetTag.class, fbTagService);
		fbs.resync(-1);
	}

}
