package net.indaba.lostandfound.asset.indexer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;

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
	public Hits search(SearchContext searchContext) throws SearchException {
		Hits hits = super.search(searchContext);

		String[] queryTerms = hits.getQueryTerms();

		String keywords = searchContext.getKeywords();

		queryTerms = ArrayUtil.append(
			queryTerms, keywords);

		hits.setQueryTerms(queryTerms);

		return hits;
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
	protected void doReindex(String[] ids) throws Exception {
		_log.error("This method is not implemented doReindex @ ItemIndexer " + ids);
	}

	@Override
	protected void doReindex(Item item) throws Exception {
		Document doc = getDocument(item);
		IndexWriterHelperUtil.updateDocument(
				getSearchEngineId(), item.getCompanyId(),
				doc, isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Item item = ItemLocalServiceUtil.getItem(classPK);
		IndexWriterHelperUtil.updateDocument(
				getSearchEngineId(), item.getCompanyId(), getDocument(item),
				isCommitImmediately());
		
	}
	

	private final Log _log = LogFactoryUtil.getLog(this.getClass());
}
