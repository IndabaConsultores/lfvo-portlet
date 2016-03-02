package net.indaba.lostandfound.portlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.service.ItemServiceUtil;

public class ItemManagerPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		_log.debug("doView");
		List<Item> items = ItemLocalServiceUtil.getItems(-1, -1);
		_log.debug("got " + items.size() + " items") ;
		renderRequest.setAttribute("items", items);
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
		item.setPublishDate(new Date());
		
		//ItemLocalServiceUtil.addOrUpdateItem(item, serviceContext);
		ItemServiceUtil.addOrUpdateItem(item, serviceContext, true);
		
		sendRedirect(actionRequest, actionResponse);
		
	}
	
	public void deleteItem(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		long itemId = ParamUtil.get(actionRequest, "itemId", 0);
		_log.debug("deleteItem " + itemId);
		//ItemLocalServiceUtil.deleteItem(itemId);
		ItemServiceUtil.deleteItem(itemId, true);
		sendRedirect(actionRequest, actionResponse);
	}
	
	public void moveItemToTrash(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {
		_log.debug("moveItemToTrash " + ParamUtil.get(actionRequest, "itemId", 0));
		
		deleteItem(actionRequest,actionResponse);
		
	}

	Log _log = LogFactoryUtil.getLog(this.getClass());
	
	public static final String PATH_EDIT_ITEM="/html/manager/edit_item.jsp";

}
