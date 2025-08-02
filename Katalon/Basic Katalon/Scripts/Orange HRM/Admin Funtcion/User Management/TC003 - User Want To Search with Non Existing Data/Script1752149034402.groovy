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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.util.KeywordUtil

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/field_Search Username'), 10)

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/field_Search Username'), "a")

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/button_Search'))

String successMessage = WebUI.getText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/alert_Not Found Data'))

String cleanedMessage = CustomKeywords.'helper.helper.cleanMessage'(successMessage)
String expectedMessage = CustomKeywords.'helper.helper.cleanMessage'(GlobalVariable.messageNotFound)

if (WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/alert_Not Found Data'), 10)) {
	if (cleanedMessage.equals(expectedMessage)) {
		KeywordUtil.logInfo("Pesan cocok: '${cleanedMessage}'")
	} else {
		KeywordUtil.logInfo("Pesan tidak cocok.\nExpected: '${expectedMessage}'\nActual: '${cleanedMessage}'")
	}
} else {
	KeywordUtil.logInfo('Pesan tidak muncul sama sekali.')
}
