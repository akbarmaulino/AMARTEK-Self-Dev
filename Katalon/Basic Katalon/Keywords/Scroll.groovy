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
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable
import org.openqa.selenium.WebElement


public class Scroll {

	@Keyword
	def scrollToTestObject(TestObject testObject, int maxScrollCount = 10) {
		String xpath = testObject.findPropertyValue('xpath')

		if (xpath == null || xpath.trim() == '') {
			println "ERROR: TestObject tidak memiliki XPath! Harap cek Object Repository."
			return
		}

		String escapedXpath = xpath.replace("'", "\\'")
		boolean elementFound = false

		for (int i = 0; i < maxScrollCount; i++) {
			elementFound = WebUI.executeJavaScript("""
				const element = document.evaluate('$escapedXpath', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
				return element !== null;
			""", [])

			if (elementFound) {
				break
			} else {
				WebUI.executeJavaScript("""
					window.scrollBy({top: 500, left: 0, behavior: 'smooth'});
				""", [])
				WebUI.delay(1)
			}
		}

		if (elementFound) {
			WebUI.executeJavaScript("""
				const element = document.evaluate('$escapedXpath', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
				if (element) {
					element.scrollIntoView({behavior: 'smooth', block: 'center'});
				}
			""", [])
			WebUI.delay(2)
		} else {
			println "Element not found after scrolling ${maxScrollCount} times: $xpath"
		}
	}

	@Keyword
	def scrollUpToTestObject(TestObject testObject, int maxScrollCount = 10) {
		String xpath = testObject.findPropertyValue('xpath')

		if (xpath == null || xpath.trim() == '') {
			println "ERROR: TestObject tidak memiliki XPath! Harap cek Object Repository."
			return
		}

		String escapedXpath = xpath.replace("'", "\\'")  // Penting: Escape kutip satu di XPath

		boolean elementFound = false

		for (int i = 0; i < maxScrollCount; i++) {
			elementFound = WebUI.executeJavaScript("""
				const element = document.evaluate('$escapedXpath', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
				return element !== null;
			""", [])

			if (elementFound) {
				break
			} else {
				WebUI.executeJavaScript("window.scrollBy(0, -100);", [])
				WebUI.delay(0.5)  // Delay kecil biar smooth-nya kelihatan nyicil
			}
		}

		if (elementFound) {
			WebUI.executeJavaScript("""
				const element = document.evaluate('$escapedXpath', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
				if (element) {
					element.scrollIntoView({behavior: 'smooth', block: 'center'});
				}
			""", [])
			WebUI.delay(2)
		} else {
			println "Element not found after scrolling ${maxScrollCount} times: $xpath"
		}
	}
	
	@Keyword
	def scrollToWebElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element)
		WebUI.delay(2)
	}
}
