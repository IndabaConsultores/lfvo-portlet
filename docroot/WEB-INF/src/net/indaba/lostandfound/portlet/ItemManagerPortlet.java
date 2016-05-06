package net.indaba.lostandfound.portlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.io.IOUtils;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import net.indaba.lostandfound.firebase.FirebaseSyncUtil;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.model.LFImage;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.service.ItemServiceUtil;
import net.indaba.lostandfound.service.LFImageLocalServiceUtil;
import net.indaba.lostandfound.service.LFImageServiceUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

public class ItemManagerPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		_log.debug("doView");
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Item> items;
		try {
			items = ItemLocalServiceUtil.getItems(themeDisplay.getScopeGroupId(), -1, -1);
			renderRequest.setAttribute("items", items);
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		super.doView(renderRequest, renderResponse);
	}

	public void addOrUpdateItem(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
		
		_log.debug("editItem " + ParamUtil.get(actionRequest, "itemId", 0));
		long itemId = ParamUtil.get(actionRequest, "itemId", 0);
		String name = ParamUtil.get(actionRequest, "name", "");
		
		Item item = null;
		if(itemId==0){
			item = ItemLocalServiceUtil.createItem(0);
		} else {
			item = ItemLocalServiceUtil.getItem(itemId);
		}
		
		item.setName(name);
		item.setGroupId(serviceContext.getScopeGroupId());
		item.setUserId(serviceContext.getUserId());
		item.setPublishDate(new Date());
		
		ItemServiceUtil.addOrUpdateItem(item, serviceContext);
		
		addItemImage(actionRequest, actionResponse, item.getItemId());
		
		sendRedirect(actionRequest, actionResponse);
		
	}
	
	public void deleteItem(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
		long itemId = ParamUtil.get(actionRequest, "itemId", 0);
		_log.debug("deleteItem " + itemId);
		ItemServiceUtil.deleteItem(itemId, serviceContext);
		sendRedirect(actionRequest, actionResponse);
	}
	
	public void deleteLfImage(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
		long lfImageId = ParamUtil.get(actionRequest, "lfImageId", 0);
		net.indaba.lostandfound.service.LFImageServiceUtil.deleteLFImage(lfImageId, serviceContext);
		sendRedirect(actionRequest, actionResponse);
	}
	
	public void moveItemToTrash(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		_log.debug("moveItemToTrash " + ParamUtil.get(actionRequest, "itemId", 0));
		
		deleteItem(actionRequest,actionResponse);
		
	}
	
	public void doDataDiagnosis(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		_log.debug("doDataDiagnosis ");
		FirebaseSyncUtil firebaseUtil = FirebaseSyncUtil.getInstance();
		try {
			firebaseUtil.updateUnsyncedItems();
			//System.out.println(firebaseUtil.getFirebaseUnsyncedItems());
		} catch (FirebaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doDataSync(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		_log.debug("doDataDiagnosis ");
		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
		FirebaseSyncUtil firebaseUtil = FirebaseSyncUtil.getInstance();
		try {
			if (true) {
				firebaseUtil.resyncItems(serviceContext);				
			}
		} catch (FirebaseException | JacksonUtilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public void addItemImage(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		long itemId = ParamUtil.getLong(actionRequest,"itemId");
		addItemImage(actionRequest, actionResponse, itemId);
	}
	
	public void addItemImage(ActionRequest actionRequest, ActionResponse actionResponse, long itemId)
			throws IOException, PortletException, PortalException {
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		File file = uploadRequest.getFile("itemImage");
		
		if(file==null) return;
		
		long _itemId = ParamUtil.getLong(uploadRequest,"itemId");
		if(_itemId==0)
			_itemId=itemId;
		_log.debug("addItemImage to item " + _itemId);
		
		
		
		String imageBase63String = Base64.encode(IOUtils.toByteArray(new FileInputStream(file)));
		ByteArrayInputStream imageBase64 = new ByteArrayInputStream(imageBase63String.getBytes(StandardCharsets.UTF_8));
		OutputBlob dataOutputBlob = new OutputBlob(imageBase64, imageBase63String.length());
		
		LFImage lfImage = LFImageLocalServiceUtil.createLFImage(CounterLocalServiceUtil.increment());
		lfImage.setItemId(_itemId);
		lfImage.setImage(dataOutputBlob);
		LFImageServiceUtil.addLFImage(lfImage, serviceContext);
	}
	
	public void addMessage(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		_log.debug("addMessage to item ");
	}

	Log _log = LogFactoryUtil.getLog(this.getClass());
	
	public static final String PATH_EDIT_ITEM="/html/manager/edit_item.jsp";

}
