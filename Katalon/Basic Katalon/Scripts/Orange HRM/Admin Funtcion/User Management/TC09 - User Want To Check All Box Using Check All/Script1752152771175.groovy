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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

// Klik checkbox Select All
WebUI.click(findTestObject('Object Repository/Orange HRM/check all box/checkbox_All Row'))

// Ambil semua checkbox row yang tercentang
List<WebElement> rowCheckboxes = WebUiCommonHelper.findWebElements(
	findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/checkbox_Checkbox'),
	10
)

// Ambil semua row di tabel
List<WebElement> totalRows = WebUiCommonHelper.findWebElements(
	findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/row_Table'),
	10
)

// Validasi apakah semua row tercentang
if (rowCheckboxes.size() == totalRows.size()-1) {
	KeywordUtil.logInfo("✅ Semua row checkbox tercentang setelah klik Select All.")
} else {
	KeywordUtil.logInfo("❌ Tidak semua row checkbox tercentang. Tercentang: ${rowCheckboxes.size()}, Total Row: ${totalRows.size()}")
}
