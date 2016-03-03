package net.indaba.lostandfound.asset;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import net.indaba.lostandfound.model.Item;

public class ItemAssetRenderer extends BaseJSPAssetRenderer<Item> {

	private Item _item;
	
	public ItemAssetRenderer(Item item) {
		_item = item;
	}
	
	@Override
    public boolean hasEditPermission(PermissionChecker permissionChecker) {
            
            return true;
    }

    @Override
    public boolean hasViewPermission(PermissionChecker permissionChecker) {

    	return true;
    }
	
	@Override
	public Item getAssetObject() {
		return _item;
	}

	@Override
	public long getGroupId() {
		return _item.getGroupId();
	}

	@Override
	public long getUserId() {
		return _item.getUserId();
	}

	@Override
	public String getUserName() {
		return "NO NAME";
	}

	@Override
	public String getUuid() {
		return null;
	}

	@Override
	public String getClassName() {
		return Item.class.getName();
	}

	@Override
	public long getClassPK() {
		return _item.getItemId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return _item.getName();
	}

	@Override
	public String getTitle(Locale arg0) {
		return _item.getName();
	}
	
	@Override
	public String getIconCssClass() throws PortalException {
		// TODO Auto-generated method stub
		return super.getIconCssClass();
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute("item", _item);

		return super.include(request, response, template);
	}

	@Override
	public String getJspPath(HttpServletRequest arg0, String arg1) {
		// TODO Auto-generated method stub
		return "/html/asset/item.jsp";
	}
	
	@Override
	public String renderActions(RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		return super.renderActions(renderRequest, renderResponse);
	}

}
