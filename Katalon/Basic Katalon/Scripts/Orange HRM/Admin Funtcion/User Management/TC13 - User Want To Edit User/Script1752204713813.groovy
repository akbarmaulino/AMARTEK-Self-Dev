import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.github.fge.jsonschema.library.Keyword
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
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))


WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> rowsEdit = driver.findElements(By.xpath("//div[@role='row']"))
for (WebElement row : rowsEdit) {
	try {
		row.findElement(By.xpath(".//div[@role='cell']//div[text()='${GlobalVariable.validSearchName}']"))
		WebElement deleteButton = row.findElement(By.xpath(".//i[contains(@class, 'bi-pencil-fill')]/ancestor::button"))
		deleteButton.click()
		KeywordUtil.logInfo("Edit button clicked for user: ${GlobalVariable.validSearchName}")
		break
	} catch (Exception e) {
	}
}

WebUI.verifyElementPresent(findTestObject("Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/field_Search Username"), 2)
WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/field_Search Username'))
WebUI.sendKeys(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/field_Search Username'), Keys.chord(Keys.CONTROL, 'a'))
WebUI.sendKeys(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/field_Search Username'), Keys.chord(Keys.BACK_SPACE))
WebUI.sendKeys(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/field_Search Username'), Keys.chord(Keys.TAB))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/field_Search Username'), GlobalVariable.validSearchNameEdit)

WebUI.click(findTestObject("Object Repository/Orange HRM/Admin Menu/User Management/Edit Page/button_Save"))

List<WebElement> rowsSearch = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/table_All Rows'),
	10)


for (int i = 0; i < rowsSearch.size(); i++) {
	String username = (rowsSearch[i]).findElement(By.xpath('.//div[@role=\'cell\'][2]//div')).getText()

	if (username.equals(GlobalVariable.validSearchNameEdit)) {
		KeywordUtil.logInfo("Baris dengan Username '${GlobalVariable.validSearchNameEdit}' ditemukan di baris ke-${i + 1}")
	}

}
