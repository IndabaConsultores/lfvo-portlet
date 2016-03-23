package net.indaba.lostandfound.hook;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.InputStream;
import java.util.List;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;

public class LRMBMessageLocalService extends MBMessageLocalServiceWrapper {
	/* (non-Java-doc)
	 * @see com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper#MBMessageLocalServiceWrapper(MBMessageLocalService mbMessageLocalService)
	 */
	public LRMBMessageLocalService(MBMessageLocalService mbMessageLocalService) {
		super(mbMessageLocalService);
	}

	@Override
	public MBMessage addMessage(long userId, String userName, long groupId, long categoryId, String subject,
			String body, String format, List<ObjectValuePair<String, InputStream>> inputStreamOVPs, boolean anonymous,
			double priority, boolean allowPingbacks, ServiceContext serviceContext) throws PortalException {
		MBMessage message = super.addMessage(userId, userName, groupId, categoryId, subject, body, format, inputStreamOVPs, anonymous,
				priority, allowPingbacks, serviceContext);
		
		return message;
	}
	
	@Override
	public MBMessage deleteMBMessage(MBMessage mbMessage) {
		
		return super.deleteMBMessage(mbMessage);
	}
}