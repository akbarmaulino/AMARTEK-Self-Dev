package helper
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File

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
				// Cek validitas file tanpa ImageIO
				int retry = 0
				while ((!file.exists() || file.length() < 2048) && retry < 10) {
					Thread.sleep(300)
					retry++
				}
	
				if (file.exists() && file.length() >= 2048) {
					KeywordUtil.markPassed("📸 Screenshot berhasil: <a href='file://${filePath}'>${file.name}</a>")
				} else {
					file.delete()
					KeywordUtil.logInfo("⚠️ Screenshot gagal dibuat (file corrupt/kosong): ${filePath}")
				}
			} else {
				KeywordUtil.logInfo("❌ Gagal ambil screenshot ke: ${filePath}")
			}
		} catch (Exception e) {
			KeywordUtil.logInfo("❌ ERROR saat safeScreenshot: ${e.message}")
		}
	}




}