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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/field_Search Username'), 10)

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/field_User Role'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/select_Role Admin'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/button_Search'))

List<WebElement> filteredRows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/table_All Rows'), 10)

KeywordUtil.logInfo('Jumlah baris hasil search: ' + filteredRows.size())

for (int i = 0; i < filteredRows.size(); i++) {
	String userRole = filteredRows[i].findElement(By.xpath(".//div[@role='cell'][3]//div")).getText()
	KeywordUtil.logInfo("Hasil Search - Baris ke-${i + 1}:UserRole=${userRole}")
}

