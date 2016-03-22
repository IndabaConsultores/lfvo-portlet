package net.indaba.lostandfound.hook;

import com.liferay.asset.kernel.service.AssetTagLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import net.indaba.lostandfound.firebase.FirebaseTagSyncUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;

public class LFAssetTagLocalService extends AssetTagLocalServiceWrapper {
	
	private FirebaseTagSyncUtil firebaseUtil = FirebaseTagSyncUtil.getInstance();
	
	/* (non-Java-doc)
	 * @see com.liferay.asset.kernel.service.AssetTagLocalServiceWrapper#AssetTagLocalServiceWrapper(AssetTagLocalService assetTagLocalService)
	 */
	public LFAssetTagLocalService(AssetTagLocalService assetTagLocalService) {
		super(assetTagLocalService);
	}
	
	@Override
	public AssetTag addTag(long userId, long groupId, String name, ServiceContext serviceContext) throws com.liferay.portal.kernel.exception.PortalException {
		AssetTag tag = super.addTag(userId, groupId, name, serviceContext);
		if (firebaseUtil.isSyncEnabled()) {
			try {
				firebaseUtil.add(tag);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tag;
	}
	
	@Override
	public AssetTag updateTag(long userId, long tagId, String name, ServiceContext serviceContext)
			throws PortalException {
		AssetTag tag = super.updateTag(userId, tagId, name, serviceContext);
		if (firebaseUtil.isSyncEnabled()) {
			try {
				firebaseUtil.update(tag);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tag;
	}
	
	@Override
	public void deleteTag(AssetTag tag) throws PortalException {
		if (firebaseUtil.isSyncEnabled()) {
			try {
				firebaseUtil.delete(tag);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		super.deleteTag(tag);
	}
	
}