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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/List Menu Admin/list_Job'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/List Menu Admin/list_Job Title'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Job Title/button_Add'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Job Title/field_Job Title'), GlobalVariable.JobTitle)

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Job Title/field_Job Description'), GlobalVariable.JobDescription)

WebUI.uploadFile(findTestObject('Orange HRM/Admin Menu/Job Title/form_Upload'), GlobalVariable.pathUploadFile)

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Job Title/field_Note'), GlobalVariable.JobNote)

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Job Title/button_Save'))

if (WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/Job Title/alert_SuccessAddNewJob'),
	10)) {
	KeywordUtil.logInfo('Pesan Berhasil untuk Daftar User Baru Berhasil')

	String successMessage = WebUI.getText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/alert_Success Create'))

	KeywordUtil.logInfo('Pesan Success: ' + successMessage)

	WebUI.takeScreenshot()
} else {
	KeywordUtil.logInfo('Pesan Berhasil tidak Muncul')
}

List<WebElement> rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/table_All Rows'),
	10)

for (int i = 0; i < rows.size(); i++) {
	String JobTitle = (rows[i]).findElement(By.xpath('.//div[@role=\'cell\'][2]//div')).getText()

	if (JobTitle.equals(GlobalVariable.JobTitle)) {
		KeywordUtil.logInfo("Baris dengan Username '${GlobalVariable.JobTitle}' ditemukan di baris ke-${i + 1}")
		WebElement targetCell = rows[i].findElement(By.xpath(".//div[@role='cell'][2]//div"))
		CustomKeywords.'Scroll.scrollToWebElement'(targetCell)

		break
	}

}

