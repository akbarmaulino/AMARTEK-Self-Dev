import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.JavascriptExecutor


//// Buka halaman
//WebUI.openBrowser('')
//WebUI.navigateToUrl('https://the-internet.herokuapp.com/broken_images')
//
//// Ambil WebDriver instance dari Katalon
//WebDriver driver = DriverFactory.getWebDriver()
//
//// Cari semua element <img>
//List<WebElement> imagesList = driver.findElements(By.tagName('img'))
//
//println("Total Gambar Ditemukan: " + imagesList.size())
//
//int brokenImageCount = 0
//
//// Loop semua gambar, cek yang rusak
//for (WebElement img : imagesList) {
//	Boolean imageDisplayed = (Boolean) ((JavascriptExecutor) driver).executeScript(
//		"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", img);
//	
//	if (!imageDisplayed) {
//		println("Broken Image Found: " + img.getAttribute("src"))
//		brokenImageCount++
//	}
//}
//
//println("Total Broken Images: " + brokenImageCount)
//
//// Tutup browser
//WebUI.closeBrowser()

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory

// Buka halaman
WebUI.openBrowser('')
WebUI.navigateToUrl('https://the-internet.herokuapp.com/broken_images')

// Ambil semua gambar di halaman
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> images = driver.findElements(By.tagName('img'))

// Cek gambar satu per satu
for (WebElement img : images) {
	Boolean isImageOk = (Boolean) ((JavascriptExecutor) driver).executeScript(
		"return arguments[0].complete && arguments[0].naturalWidth > 0;", img);
	
	if (isImageOk) {
		println("Gambar OK: " + img.getAttribute('src'))
	} else {
		println("Gambar BROKEN: " + img.getAttribute('src'))
	}
}

WebUI.closeBrowser()

