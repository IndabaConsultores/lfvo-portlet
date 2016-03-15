package net.indaba.lostandfound.hook;

import com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import net.indaba.lostandfound.firebase.FirebaseCategorySyncUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;

public class LFAssetCategoryLocalService extends AssetCategoryLocalServiceWrapper {
	
	private FirebaseCategorySyncUtil firebaseUtil = FirebaseCategorySyncUtil.getInstance();
	
	/* (non-Java-doc)
	 * @see com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper#AssetCategoryLocalServiceWrapper(AssetCategoryLocalService assetCategoryLocalService)
	 */
	public LFAssetCategoryLocalService(AssetCategoryLocalService assetCategoryLocalService) {
		super(assetCategoryLocalService);
	}
	
	@Override
	public AssetCategory addCategory(long userId, long groupId, long parentCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext) throws PortalException {
		AssetCategory category = super.addCategory(userId, groupId, parentCategoryId, titleMap, descriptionMap, vocabularyId, categoryProperties,
				serviceContext);
		if (firebaseUtil.isSyncEnabled()) {
			try {
				firebaseUtil.add(category);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return category;
	}
	
	@Override
	public AssetCategory updateCategory(long userId, long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext) throws PortalException {
		AssetCategory category = super.updateCategory(userId, categoryId, parentCategoryId, titleMap, descriptionMap, vocabularyId,
				categoryProperties, serviceContext);
		if (firebaseUtil.isSyncEnabled()) {
			try {
				firebaseUtil.update(category);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		return category;
	}
	
	@Override
	public AssetCategory deleteCategory(AssetCategory category, boolean skipRebuildTree) throws PortalException {
		if (firebaseUtil.isSyncEnabled()) {
			try {
				firebaseUtil.delete(category);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.deleteCategory(category, skipRebuildTree);
	}

}