package net.indaba.lostandfound.hook;

import java.io.UnsupportedEncodingException;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;

import net.indaba.lostandfound.firebase.FirebaseGroupSyncUtil;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

public class LFGroupModelListener extends BaseModelListener<Group> {

	private FirebaseGroupSyncUtil firebaseUtil = FirebaseGroupSyncUtil.getInstance();

	private boolean updateFirebase(Group group) {
		return (firebaseUtil.isSyncEnabled() && group.getSite() 
				&& group.getClassName().equals(Group.class.getName()));
	}

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		if (updateFirebase(group)) {
			try {
				firebaseUtil.add(group);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onAfterCreate(group);
	}

	@Override
	public void onAfterUpdate(Group group) throws ModelListenerException {
		if (updateFirebase(group)) {
			try {
				firebaseUtil.update(group);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onAfterUpdate(group);
	}

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		if (updateFirebase(group)) {
			try {
				firebaseUtil.delete(group);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.onAfterRemove(group);
	}

}
