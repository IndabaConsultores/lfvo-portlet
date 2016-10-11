package net.indaba.lostandfound.util;

import java.io.File;
import com.liferay.util.portlet.PortletProps;

public class AppFileUtil {
	
	static private String baseDir = PortletProps.get("lfvo.apks.dir");
	static private String apkFilename = "lfvoApp.apk";
	static private String iconFilename = "icon.png";
	static private String splashFilename = "splash.png";
	
	private AppFileUtil() {}
	
	public static boolean apkExistsForSite(long groupId) {
		return getApkForSite(groupId).exists();
	}
	
	public static File getApkForSite(long groupId) {
		String filepath = baseDir + "lfvo" + groupId + "/" + apkFilename;
		File file = new File(filepath);
		return file;
	}
	
	public static File getIconForSite(long groupId) {
		String filepath = baseDir + "lfvo" + groupId + "/" + iconFilename;
		File file = new File(filepath);
		return file;
	}
	
	public static File getSplashForSite(long groupId) {
		String filepath = baseDir + "lfvo" + groupId + "/" + splashFilename;
		File file = new File(filepath);
		return file;
	}

}
