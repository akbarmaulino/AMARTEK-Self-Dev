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

import org.openqa.selenium.Keys
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory

public class helper {

	@Keyword
	def cleanMessage(String message) {
		return message.replaceAll("\\s+", " ").trim()
	}

	@Keyword
	def clearAndSetText(TestObject testObject, String text) {
		WebUI.sendKeys(testObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(testObject, Keys.chord(Keys.BACK_SPACE))  // Hapus semua
		WebUI.setText(testObject, text)  // Isi dengan text baru
	}


	@Keyword
	def forceClearTextUsingJS(TestObject testObject) {
		String xpath = testObject.findPropertyValue('xpath')
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()

		js.executeScript("""
			var el = document.evaluate('${xpath}', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
			if (el) {
				el.value = '';
				el.dispatchEvent(new Event('input', { bubbles: true }));
				el.dispatchEvent(new Event('change', { bubbles: true }));
				el.dispatchEvent(new Event('blur', { bubbles: true }));
				el.dispatchEvent(new KeyboardEvent('keydown', { bubbles: true, key: 'a' }));
			}
		""", [])
	}
	
	@Keyword
	def forceClearByIndex(int index = 1) { 
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		js.executeScript("""
		var nodes = document.evaluate("//input[@class='oxd-input oxd-input--active']", document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
		if (nodes.snapshotLength > ${index}) {
			var el = nodes.snapshotItem(${index});
			el.value = '';
			el.dispatchEvent(new Event('input', { bubbles: true }));
			el.dispatchEvent(new Event('change', { bubbles: true }));
			el.dispatchEvent(new Event('blur', { bubbles: true }));
		}
	""", [])
	}
	
	@Keyword
	def clearFieldBasedOnInputLength(TestObject testObject, String previousInput) {
		WebUI.click(testObject)
//		WebUI.sendKeys(testObject, Keys.chord(Keys.CONTROL, 'a'))
		
		for (int i = 0; i < previousInput.length(); i++) {
			WebUI.sendKeys(testObject, Keys.chord(Keys.BACK_SPACE))
			WebUI.delay(0)
		}
		WebUI.sendKeys(testObject, Keys.chord(Keys.TAB))
	}
	
	
}
