package net.indaba.lostandfound.hook;

import java.io.UnsupportedEncodingException;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import net.indaba.lostandfound.firebase.FirebaseMBMessageSyncUtil;
import net.indaba.lostandfound.model.Item;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

public class LFMBMessageLocalService extends MBMessageLocalServiceWrapper {

	FirebaseMBMessageSyncUtil firebaseUtil = FirebaseMBMessageSyncUtil.getInstance();

	/*
	 * (non-Java-doc)
	 * 
	 * @see
	 * com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper#
	 * MBMessageLocalServiceWrapper(MBMessageLocalService mbMessageLocalService)
	 */
	public LFMBMessageLocalService(MBMessageLocalService mbMessageLocalService) {
		super(mbMessageLocalService);
	}

	@Override
	public MBMessage addDiscussionMessage(long userId, String userName, long groupId, String className, long classPK,
			long threadId, long parentMessageId, String subject, String body, ServiceContext serviceContext)
					throws PortalException {

		User u = UserLocalServiceUtil.getUser(userId);
		Company c = CompanyLocalServiceUtil.getCompany(u.getCompanyId());
		
		ThemeDisplay themeDisplay = (ThemeDisplay)serviceContext.getRequest().getAttribute(
				WebKeys.THEME_DISPLAY);
		if(themeDisplay==null){
			ThemeDisplay tt = new ThemeDisplay();
			tt.setCompany(c);
			tt.setScopeGroupId(groupId);
			tt.setDoAsGroupId(groupId);
			//serviceContext.setPlid(20236);
			serviceContext.getRequest().setAttribute(WebKeys.THEME_DISPLAY, tt);
		}
		
		
		MBMessage message = super.addDiscussionMessage(userId, userName, groupId, className, classPK, threadId,
				parentMessageId, subject, body, serviceContext);
		
		if (className.equals(Item.class.getName()) && firebaseUtil.isSyncEnabled()) {
			/* Only replicate if message belongs to an Item */
			try {
				firebaseUtil.add(message);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}

	@Override
	public MBMessage updateDiscussionMessage(long userId, long messageId, String className, long classPK,
			String subject, String body, ServiceContext serviceContext) throws PortalException {
		MBMessage message = super.updateDiscussionMessage(userId, messageId, className, classPK, subject, body,
				serviceContext);
		if (className.equals(Item.class.getName()) && firebaseUtil.isSyncEnabled()) {
			/* Only replicate if message belongs to an Item */
			try {
				firebaseUtil.update(message);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}

	@Override
	public MBMessage deleteDiscussionMessage(long messageId) throws PortalException {
//		MBMessage message = fetchMBMessage(messageId);
//		if (message.getClassName().equals(Item.class.getName()) && firebaseUtil.isSyncEnabled()) {
//			/* Only replicate if message belongs to an Item */
//			try {
//				firebaseUtil.delete(message);
//			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return super.deleteDiscussionMessage(messageId);
	}
	
	@Override
	public MBMessage deleteMessage(MBMessage message) throws PortalException {
		if (message.getClassName().equals(Item.class.getName()) && firebaseUtil.isSyncEnabled()) {
			/* Only replicate if message belongs to an Item */
			try {
				firebaseUtil.delete(message);
			} catch (UnsupportedEncodingException | FirebaseException | JacksonUtilityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.deleteMessage(message);
	}

}