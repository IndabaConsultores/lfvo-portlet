package net.indaba.lostandfound.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

public class ItemManagerPortlet extends MVCPortlet {

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		_log.debug("doView");
		super.doView(renderRequest, renderResponse);
	}

	public void editItem(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		_log.debug("editItem " + ParamUtil.get(actionRequest, "itemId", 0));
	}

	Log _log = LogFactoryUtil.getLog(this.getClass());

}
