package net.indaba.lostandfound.hook;

import java.util.Locale;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import net.indaba.lostandfound.firebase.FirebaseService;
import net.indaba.lostandfound.firebase.FirebaseSynchronizer;

public class LFAssetCategoryLocalService extends AssetCategoryLocalServiceWrapper {

	private FirebaseService<AssetCategory> firebaseUtil = FirebaseSynchronizer.getInstance()
			.getService(AssetCategory.class);

	/*
	 * (non-Java-doc)
	 * 
	 * @see com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper#
	 * AssetCategoryLocalServiceWrapper(AssetCategoryLocalService
	 * assetCategoryLocalService)
	 */
	public LFAssetCategoryLocalService(AssetCategoryLocalService assetCategoryLocalService) {
		super(assetCategoryLocalService);
	}

	@Override
	public AssetCategory addCategory(long userId, long groupId, long parentCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, long vocabularyId, String[] categoryProperties,
			ServiceContext serviceContext) throws PortalException {
		AssetCategory category = super.addCategory(userId, groupId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryProperties, serviceContext);
		if (firebaseUtil.isSyncEnabled()) {
			firebaseUtil.add(category);
		}
		return category;
	}

	@Override
	public AssetCategory updateCategory(long userId, long categoryId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, long vocabularyId,
			String[] categoryProperties, ServiceContext serviceContext) throws PortalException {
		AssetCategory category = super.updateCategory(userId, categoryId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryProperties, serviceContext);
		if (firebaseUtil.isSyncEnabled()) {
			firebaseUtil.update(category);

		}
		return category;
	}

	@Override
	public AssetCategory deleteCategory(AssetCategory category, boolean skipRebuildTree) throws PortalException {
		if (firebaseUtil.isSyncEnabled()) {
			firebaseUtil.delete(category);
		}
		return super.deleteCategory(category, skipRebuildTree);
	}

}