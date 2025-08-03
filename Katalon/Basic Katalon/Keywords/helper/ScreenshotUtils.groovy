package helper

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class ScreenshotUtils {

	@Keyword
	static void safeScreenshot(String identifier = null) {
		try {
			long timestamp = System.currentTimeMillis()
			String reportDir = RunConfiguration.getReportFolder()
			String screenshotDir = reportDir + "/screenshots/"
			new File(screenshotDir).mkdirs()
	
			if (identifier == null || identifier.trim() == "") {
				identifier = "capture"
			}
	
			String safeName = identifier.replaceAll("[^a-zA-Z0-9_\\-]", "_")
			String filePath = "${screenshotDir}${safeName}_${timestamp}.png"
			File file = new File(filePath)
	
			boolean success = WebUI.takeScreenshot(filePath)
	
			if (success) {
				int retry = 0
				while ((!file.exists() || file.length() < 2048) && retry < 10) {
					Thread.sleep(300)
					retry++
				}
	
				if (file.exists() && file.length() >= 2048) {
					KeywordUtil.logInfo("✅ Screenshot saved to ${filePath}") // <- no preview
				} else {
					file.delete()
					KeywordUtil.logInfo("⚠️ Screenshot corrupt or not created properly: ${filePath}")
				}
			} else {
				KeywordUtil.logInfo("❌ Failed to take screenshot to: ${filePath}")
			}
		} catch (Exception e) {
			KeywordUtil.logInfo("❌ ERROR during safeScreenshot: ${e.message}")
		}
	}

}
