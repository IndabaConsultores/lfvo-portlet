package net.indaba.lostandfound.hook;

import java.io.UnsupportedEncodingException;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.model.MBThread;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.util.portlet.PortletProps;

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
	
	private boolean updateFirebase(MBMessage message, ServiceContext serviceContext) {
		ThemeDisplay themeDisplay = new ThemeDisplay();
		if (serviceContext != null) {
			themeDisplay = (ThemeDisplay) serviceContext.getRequest().getAttribute(
					WebKeys.THEME_DISPLAY);
		}
		return (firebaseUtil.isSyncEnabled()
				&& message.getClassName().equals(Item.class.getName())
				&& themeDisplay != null);
	}

	@Override
	public MBMessage addDiscussionMessage(long userId, String userName, long groupId, String className, long classPK,
			long threadId, long parentMessageId, String subject, String body, ServiceContext serviceContext)
					throws PortalException {

		User u = UserLocalServiceUtil.getUser(userId);
		Company c = CompanyLocalServiceUtil.getCompany(u.getCompanyId());
		
		ThemeDisplay themeDisplay = (ThemeDisplay)serviceContext.getRequest().getAttribute(
				WebKeys.THEME_DISPLAY);
		boolean isThemeDisplayNull = (themeDisplay == null);
		if(themeDisplay==null) {
			ThemeDisplay tt = new ThemeDisplay();
			tt.setCompany(c);
			tt.setSiteGroupId(groupId);
			tt.setScopeGroupId(groupId);
			tt.setDoAsGroupId(groupId);
			//serviceContext.setPlid(20236);
			serviceContext.getRequest().setAttribute(WebKeys.THEME_DISPLAY, tt);
		}
		
		//Check existing thread for item classPK
		if (threadId == 0 && parentMessageId == 0) {
			MBMessageDisplay mbMessageDisplay = super.getDiscussionMessageDisplay(
					userId, groupId, Item.class.getName(), 
					classPK, WorkflowConstants.STATUS_APPROVED);
			MBThread thread = mbMessageDisplay.getThread();
			threadId = thread.getThreadId();
			parentMessageId = thread.getRootMessageId();
		}
		
		MBMessage message = super.addDiscussionMessage(userId, userName, groupId, className, classPK, threadId,
				parentMessageId, subject, body, serviceContext);

		if (updateFirebase(message, serviceContext) && !isThemeDisplayNull) {
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
		User u = UserLocalServiceUtil.getUser(userId);
		Company c = CompanyLocalServiceUtil.getCompany(u.getCompanyId());
		
		MBMessage message = getMBMessage(messageId);
		long groupId = message.getGroupId();
		
		ThemeDisplay themeDisplay = (ThemeDisplay)serviceContext.getRequest().getAttribute(
				WebKeys.THEME_DISPLAY);
		boolean isThemeDisplayNull = (themeDisplay == null);
		if(themeDisplay==null) {
			ThemeDisplay tt = new ThemeDisplay();
			tt.setCompany(c);
			tt.setSiteGroupId(groupId);
			tt.setScopeGroupId(groupId);
			tt.setDoAsGroupId(groupId);
			//serviceContext.setPlid(20236);
			serviceContext.getRequest().setAttribute(WebKeys.THEME_DISPLAY, tt);
		}
		
		message = super.updateDiscussionMessage(userId, messageId, className, classPK, subject, body,
				serviceContext);
		if (updateFirebase(message, serviceContext) && !isThemeDisplayNull) {
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
	public MBMessage deleteMessage(MBMessage message) throws PortalException {
		if (updateFirebase(message, null)) {
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