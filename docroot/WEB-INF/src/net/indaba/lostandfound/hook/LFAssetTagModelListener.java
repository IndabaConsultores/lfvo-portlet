package net.indaba.lostandfound.hook;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;

import net.indaba.lostandfound.firebase.FirebaseService;
import net.indaba.lostandfound.firebase.FirebaseSynchronizer;
import net.indaba.lostandfound.model.Item;
import net.indaba.lostandfound.service.ItemLocalServiceUtil;

public class LFAssetTagModelListener extends BaseModelListener<AssetTag> {

	private FirebaseService<AssetTag> getFbService() {
		return FirebaseSynchronizer.getInstance().getService(AssetTag.class);
	}

	private boolean updateFirebase(AssetTag tag) {
		return (getFbService().isSyncEnabled());
	}

	@Override
	public void onAfterCreate(AssetTag tag) throws ModelListenerException {
		if (updateFirebase(tag)) {
			getFbService().add(tag);
		}
		super.onAfterCreate(tag);
	}

	@Override
	public void onAfterUpdate(AssetTag tag) throws ModelListenerException {
		if (updateFirebase(tag)) {
			getFbService().update(tag, null);
		}
		super.onAfterUpdate(tag);
	}

	@Override
	public void onAfterRemove(AssetTag tag) throws ModelListenerException {
		if (updateFirebase(tag)) {
			getFbService().delete(tag);
		}
		super.onAfterRemove(tag);
	}

}
