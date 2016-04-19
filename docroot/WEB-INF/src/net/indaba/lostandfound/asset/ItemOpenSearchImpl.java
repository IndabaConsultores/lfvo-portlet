package net.indaba.lostandfound.asset;

import javax.portlet.PortletURL;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import net.indaba.lostandfound.model.Item;

public class ItemOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/lost_and_found/open_search";

	public static final String TITLE = "Lost and found item search: ";

	@Override
	public Indexer<Item> getIndexer() {
		return IndexerRegistryUtil.getIndexer(Item.class);
	}

	@Override
	public String getSearchPath() {
		return SEARCH_PATH;
	}

	@Override
	public String getTitle(String keywords) {
		return TITLE + keywords;
	}


	@Override
	protected String getURL(ThemeDisplay themeDisplay, long groupId, com.liferay.portal.kernel.search.Document result,
			PortletURL portletURL) throws Exception {
		// TODO Auto-generated method stub
		return "ITEM_URL";
		
	}
	
}
