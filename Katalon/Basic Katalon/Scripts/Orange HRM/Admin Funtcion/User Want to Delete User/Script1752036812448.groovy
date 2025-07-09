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
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/button_Add'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_User Role'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/select_Role Admin'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_Employee Name'), 'Test')

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/select_Name'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_User Status'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/select_Status Enabled'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_username'), GlobalVariable.addUsername)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_Confrim Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/button_Save'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/field_Search Username'), 10)

WebUI.waitForElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/row_Table'), 10)

def driver = DriverFactory.getWebDriver()

List<WebElement> rows = driver.findElements(By.xpath('//div[@role=\'row\']'))

for (WebElement row : rows) {
    try {
        WebElement usernameCell = row.findElement(By.xpath(('.//div[@role=\'cell\']//div[text()=\'' + GlobalVariable.addUsername) + 
                '\']'))

        WebElement deleteButton = row.findElement(By.xpath('.//i[contains(@class, \'bi-trash\')]/ancestor::button'))

        deleteButton.click()

        KeywordUtil.logInfo("Delete button clicked for user: $GlobalVariable.addUsername")

        break
    }
    catch (Exception e) {
        continue
    } 
}

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/button_Confrim Delete'))

if (WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/alert_Success Delete'), 10)) {
    KeywordUtil.logInfo('Pesan Berhasil untuk Hapus User Berhasil')

    String successDelete = WebUI.getText(findTestObject('Object Repository/Orange HRM/Admin Menu/alert_Success Delete'))

    KeywordUtil.logInfo('Pesan Success Delete: ' + successDelete)

    WebUI.takeScreenshot()

    // Search ulang username untuk validasi
    WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/field_Search Username'), GlobalVariable.addUsername)

    WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/button_Search'))

    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/alert_Not Found Data'), 10)) {
        KeywordUtil.logInfo("User $GlobalVariable.addUsername berhasil dihapus. 'No Records Found' muncul.")

        WebUI.takeScreenshot() // Cek lagi di tabel (looping baris)
    } else {
        KeywordUtil.logInfo('\'No Records Found\' tidak muncul, cek langsung di tabel...')

        List<WebElement> searchRows = driver.findElements(By.xpath('//div[@role=\'row\']'))

        boolean userFound = false

        for (WebElement row : searchRows) {
            try {
                row.findElement(By.xpath(('.//div[@role=\'cell\']//div[text()=\'' + GlobalVariable.addUsername) + '\']'))

                userFound = true

                break
            }
            catch (Exception e) {
                continue
            } 
        }
        
        if (!(userFound)) {
            KeywordUtil.logInfo("✅ User $GlobalVariable.addUsername tidak ditemukan di tabel. Berhasil dihapus.")
        } else {
            KeywordUtil.logInfo("❌ User $GlobalVariable.addUsername masih ada di tabel. Gagal dihapus.")
        }
    }
} else {
    KeywordUtil.logInfo('❌ Pesan Berhasil tidak Muncul setelah hapus user.')
}

