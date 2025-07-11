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
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/List Menu Admin/list_Job'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/List Menu Admin/list_Job Title'))

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> rowsEdit = driver.findElements(By.xpath("//div[@role='row']"))
for (WebElement row : rowsEdit) {
	try {
		row.findElement(By.xpath(".//div[@role='cell']//div[text()='${GlobalVariable.jobTitleEdit}']"))
		WebElement deleteButton = row.findElement(By.xpath(".//i[contains(@class, 'bi-trash')]/ancestor::button"))
		deleteButton.click()
		KeywordUtil.logInfo("Delete button clicked for user: ${GlobalVariable.jobTitleEdit}")
		break
	} catch (Exception e) {
	}
}
WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/button_Confrim Delete'))

// Validasi pesan delete
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/alert_Success Delete'), 10)) {
	KeywordUtil.logInfo('✅ Pesan Berhasil untuk Hapus User Berhasil')
	String successDelete = WebUI.getText(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/alert_Success Delete'))
	KeywordUtil.logInfo("Pesan Success Delete: ${successDelete}")
	WebUI.takeScreenshot()
	
	List<WebElement> rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/User Management/Main Menu/table_All Rows'), 10)
	
	for (int i = 0; i < rows.size(); i++) {
		String JobTitle = rows[i].findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
		if (JobTitle == GlobalVariable.jobTitleEdit) {
			KeywordUtil.logInfo("Baris dengan Job Title '${GlobalVariable.jobTitleEdit}' ditemukan di baris ke-${i + 1}")
			KeywordUtil.logInfo("Baris belum berhasil terhapus")
		}else {
			KeywordUtil.logInfo("${GlobalVariable.jobTitleEdit}' Berhasil Dihapus'")
		}
	}
} else {
	KeywordUtil.logInfo('❌ Pesan Berhasil tidak muncul setelah hapus user.')
}

