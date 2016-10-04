package net.indaba.lostandfound.portlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;

/**
 * Portlet implementation class ApkDownloadPortlet
 */
public class ApkDownloadPortlet extends MVCPortlet {
	
	private Boolean checkApk(long groupId) {
		String filename = "lfvoApp_" + groupId + "/lfvoApp.apk";
		//TODO set path/to/apk in server
		String filepath = PortletProps.get("lfvo.apks.dir") + filename;
		File file = new File(filepath);
		return file.exists();
	}
	
	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException,
			PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		Boolean apkCreated = checkApk(groupId);
		renderRequest.setAttribute("apkCreated", apkCreated);
		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		String resource = resourceRequest.getParameter("resource");
		ThemeDisplay td = (ThemeDisplay) resourceRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		long groupId = td.getSiteGroupId();
		String filename = "", 
				filepath = "",
				contentType = "";
		
		switch (resource) {
		case "apk":
			filename = "lfvoApp_" + groupId + "/lfvoApp.apk";
			filepath = PortletProps.get("lfvo.apks.dir") + filename;
			contentType = "application/vnd.android.package-archive";
			break;
		case "icon":
			filename = "lfvoApp_" + groupId + "/icon.png";
			filepath = PortletProps.get("lfvo.apks.dir") + filename;
			contentType = "image/png";
			break;
		}
		
		File file = new File(filepath);
		FileInputStream in = new FileInputStream(file);

		HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(
				resourceResponse);
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(
				resourceRequest);
		ServletResponseUtil.sendFile(httpReq, httpRes, file.getName(), in,
				contentType);
		super.serveResource(resourceRequest, resourceResponse);
	}

}
