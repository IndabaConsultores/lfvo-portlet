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

import net.indaba.lostandfound.util.AppFileUtil;

/**
 * Portlet implementation class ApkDownloadPortlet
 */
public class ApkDownloadPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException,
			PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		Boolean apkCreated = AppFileUtil.apkExistsForSite(groupId);
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
		String contentType = "";
		File file = null;
		switch (resource) {
		case "apk":
			file = AppFileUtil.getApkForSite(groupId);
			contentType = "application/vnd.android.package-archive";
			break;
		case "icon":
			file = AppFileUtil.getIconForSite(groupId);
			contentType = "image/png";
			break;
		}
		
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
