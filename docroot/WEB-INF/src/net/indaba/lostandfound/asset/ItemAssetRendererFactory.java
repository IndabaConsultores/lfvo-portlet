package net.indaba.lostandfound.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;

public class ItemAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = Item.class.getName();

	public static final String TYPE = "item";

	@Override
	public AssetRenderer getAssetRenderer(long itemId, int type) throws PortalException {

		Item item = ItemLocalServiceUtil.getItem(itemId);

		return new ItemAssetRenderer(item);
	}
	
	@Override
    public String getClassName() {
            return CLASS_NAME;
    }

	@Override
	public String getType() {
		return TYPE;
	}
	
	@Override
    public boolean isLinkable() {
            return _LINKABLE;
    }

    private static final boolean _LINKABLE = false;

	

}
