package net.indaba.lostandfound.asset;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;
import net.indaba.lostandfound.util.LFVOConstants;

public class ItemAssetRendererFactory extends BaseAssetRendererFactory<Item> {

	public static final String CLASS_NAME = Item.class.getName();

	public static final String TYPE = "item";

	@Override
	public AssetRenderer<Item> getAssetRenderer(long itemId, int type) throws PortalException {

		Item item = ItemLocalServiceUtil.getItem(itemId);

		return new ItemAssetRenderer(item);
	}
	
	@Override
    public String getClassName() {
            return CLASS_NAME;
    }
	
	@Override
	public String getIconCssClass() {
		return "icon-file";
	}
	
	@Override
	public String getPortletId() {
		return LFVOConstants.LFVO_PORTLET_ID;
	}

	@Override
	public String getType() {
		return TYPE;
	}
	
	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			liferayPortletRequest, "lfvo",
			getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/html/manager/edit_item.jsp");

		return portletURL;
	}
	
	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return true;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return true;
	}

	
	@Override
    public boolean isLinkable() {
            return _LINKABLE;
    }

    private static final boolean _LINKABLE = false;

	

}
