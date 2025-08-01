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
	static void safeScreenshot(String filePath = null) {
		try {
			if (filePath == null || filePath.trim() == "") {
				long timestamp = System.currentTimeMillis()
				String reportDir = RunConfiguration.getReportFolder()
				filePath = "${reportDir}/screenshot_${timestamp}.png"
			}

			boolean success = WebUI.takeScreenshot(filePath)
			if (success) {
				File file = new File(filePath)
				BufferedImage img = ImageIO.read(file)
				if (img != null) {
					ImageIO.write(img, "png", file) // Rewrite dengan format valid
					KeywordUtil.logInfo("✅ Screenshot aman disimpan: ${filePath}")
				} else {
					KeywordUtil.logInfo("⚠️ Screenshot berhasil dibuat tapi gagal dibaca ulang (bisa jadi formatnya error): ${filePath}")
				}
			} else {
				KeywordUtil.logInfo("❌ Gagal ambil screenshot ke: ${filePath}")
			}
		} catch (Exception e) {
			KeywordUtil.logInfo("❌ ERROR saat ambil screenshot aman: ${e.message}")
		}
	}
}
