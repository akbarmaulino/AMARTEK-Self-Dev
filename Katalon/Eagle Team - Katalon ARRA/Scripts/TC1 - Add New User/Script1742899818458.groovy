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

WebUI.callTestCase(findTestCase('TC1 - Login'), [('Username') : 'Usermaker', ('Password') : 'P@ssw0rd'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Sidebar/sidebar - Global'))

WebUI.click(findTestObject('Object Repository/Sidebar/sidebar - Security'))

WebUI.click(findTestObject('Object Repository/Sidebar/sidebar - User'))

WebUI.click(findTestObject('Object Repository/User Menu Page/button - Add'))

WebUI.setText(findTestObject('Object Repository/User Menu Page/field - User ID'), 'Test123')

WebUI.setEncryptedText(findTestObject('Object Repository/User Menu Page/field - Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.setEncryptedText(findTestObject('Object Repository/User Menu Page/field - Password Confrimation'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.setText(findTestObject('Object Repository/User Menu Page/field - First Name'), 'Test')

WebUI.setText(findTestObject('Object Repository/User Menu Page/field - Last Name'), '123')

WebUI.setText(findTestObject('Object Repository/User Menu Page/field - Employee ID'), '123Test')

WebUI.setText(findTestObject('Object Repository/User Menu Page/field - Position'), 'Test')

WebUI.setText(findTestObject('User Menu Page/field - Menu Grup'), 'CRUD_Approval - CRUD_Approval')

WebUI.sendKeys(findTestObject('User Menu Page/field - Menu Grup'), Keys.chord(Keys.ENTER))

WebUI.setText(findTestObject('User Menu Page/field - Branch Group'), 'ALL_BRANCH - ALL BRANCH')

WebUI.sendKeys(findTestObject('User Menu Page/field - Branch Group'), Keys.chord(Keys.ENTER))

WebUI.setText(findTestObject('User Menu Page/field - Approval Group'), 'CRUD_Approval - CRUD_Approval')

WebUI.sendKeys(findTestObject('User Menu Page/field - Approval Group'), Keys.chord(Keys.ENTER))

WebUI.setText(findTestObject('User Menu Page/field - Module Access'), 'GLOBAL - GLOBAL')

WebUI.sendKeys(findTestObject('User Menu Page/field - Module Access'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/User Menu Page/button - Save'))

if (WebUI.waitForElementPresent(findTestObject('Object Repository/User Menu Page/alert - Save Data Successfully'), 5, FailureHandling.STOP_ON_FAILURE)) {
    WebUI.takeFullPageScreenshot(FailureHandling.STOP_ON_FAILURE)

    WebUI.takeFullPageScreenshotAsCheckpoint('User Berhasil Menambahkan User Baru')
} else {
    WebUI.takeFullPageScreenshot(FailureHandling.STOP_ON_FAILURE)

    WebUI.takeFullPageScreenshotAsCheckpoint('User Gagal Membuat User Baru')
}

