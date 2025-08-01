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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/button_Add'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/field_User Role'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/select_Role Admin'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/field_Employee Name'), GlobalVariable.employeeName)


String xpath_select_name = "//div[@role='option' and .//span[text()='" + GlobalVariable.employeeName + "']]"

TestObject select_name = new TestObject()
select_name.addProperty("xpath", ConditionType.EQUALS, xpath_select_name)

WebUI.click(select_name)


//WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/select_Name'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/field_User Status'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/select_Status Enabled'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/field_username'), GlobalVariable.addUsername)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/field_Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/field_Confrim Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/button_Save'))

if (WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/alert_Success Create'), 
    10)) {
    KeywordUtil.logInfo('Pesan Berhasil untuk Daftar User Baru Berhasil')

    String successMessage = WebUI.getText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Add Page/alert_Success Create'))

    KeywordUtil.logInfo('Pesan Success: ' + successMessage)

    WebUI.takeScreenshot()
} else {
    KeywordUtil.logInfo('Pesan Berhasil tidak Muncul')
}

WebUI.waitForElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/field_Search Username'), 10)

WebUI.delay(3)

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/field_Search Username'), GlobalVariable.addUsername)

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/button_Search'))

WebUI.delay(3)

List<WebElement> rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/table_All Rows'), 
    10)

// ✅ Case 1: Validasi Baris Hasil Search
//KeywordUtil.logInfo('Total baris ditemukan: ' + rows.size())
//
//for (int i = 0; i < rows.size(); i++) {
//	String username = rows[i].findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	String userRole = rows[i].findElement(By.xpath(".//div[@role='cell'][3]//div")).getText()
//	String employeeName = rows[i].findElement(By.xpath(".//div[@role='cell'][4]//div")).getText()
//	String status = rows[i].findElement(By.xpath(".//div[@role='cell'][5]//div")).getText()
//	KeywordUtil.logInfo(" ")
//	KeywordUtil.logInfo("Baris ke-${i + 1}: Username=${username}, UserRole=${userRole}, EmployeeName=${employeeName}, Status=${status}")
//}
//// ✅ SOAL 2: Validasi Baris Tertentu Saja
//KeywordUtil.logInfo("=== Mulai Validasi Baris Tertentu ===")
//// Misal: Validasi hanya baris ke-1 dan ke-3 jika ada
//if (rows.size() >= 1) {
//	WebElement row1 = rows[0]
//	String username1 = row1.findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	KeywordUtil.logInfo("Baris ke-1, Username: " + username1)
//}
//
//if (rows.size() >= 3) {
//	WebElement row3 = rows[2]
//	String username3 = row3.findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	KeywordUtil.logInfo("Baris ke-3, Username: " + username3)
//}
//
// Atau berdasarkan nama tertentu, misalnya 'Mineeee':
for (int i = 0; i < rows.size(); i++) {
    String username = (rows[i]).findElement(By.xpath('.//div[@role=\'cell\'][2]//div')).getText()

    if (username.equals(GlobalVariable.addUsername)) {
		KeywordUtil.logInfo("Baris dengan Username '${GlobalVariable.addUsername}' ditemukan di baris ke-${i + 1}")
	}

}


