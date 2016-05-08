package net.indaba.lostandfound.hook;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;

import net.indaba.lostandfound.firebase.FirebaseService;
import net.indaba.lostandfound.firebase.FirebaseSynchronizer;

public class LFGroupModelListener extends BaseModelListener<Group> {

	private FirebaseService<Group> firebaseUtil = (FirebaseService<Group>) FirebaseSynchronizer.getInstance().getService(Group.class);

	private boolean updateFirebase(Group group) {
		return (firebaseUtil.isSyncEnabled() && group.getSite() 
				&& group.getClassName().equals(Group.class.getName()));
	}

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		if (updateFirebase(group)) {
			firebaseUtil.add(group);
		}
		super.onAfterCreate(group);
	}

	@Override
	public void onAfterUpdate(Group group) throws ModelListenerException {
		if (updateFirebase(group)) {
			firebaseUtil.update(group);
		}
		super.onAfterUpdate(group);
	}

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		if (updateFirebase(group)) {
			firebaseUtil.delete(group);
		}
		super.onAfterRemove(group);
	}

}
