package net.indaba.lostandfound.asset.indexer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;

import net.indaba.lostandfound.model.Item;

public class ItemIndexer extends BaseIndexer<Item> {
	
	public static final String CLASS_NAME = Item.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}
	
	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return true;
	}
	

	@Override
	protected void doDelete(Item item) throws Exception {
		deleteDocument(item.getCompanyId(), item.getPrimaryKey());
	}

	@Override
	protected Document doGetDocument(Item item) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, item);

		document.addText(Field.DESCRIPTION, item.getDescription());
		document.addText(Field.TITLE, item.getName());

		document.addKeyword("titleKeyword", item.getName(), true);

		return document;
	}

	@Override
	protected Summary doGetSummary(Document document, Locale locale, String snippet, PortletRequest portletRequest, PortletResponse portletResponse)
			throws Exception {
		String title = document.get(Field.TITLE);
		String content = snippet;
		return new Summary(title, content);
	}

	@Override
	protected void doReindex(String[] arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doReindex(Item arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doReindex(String arg0, long arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
