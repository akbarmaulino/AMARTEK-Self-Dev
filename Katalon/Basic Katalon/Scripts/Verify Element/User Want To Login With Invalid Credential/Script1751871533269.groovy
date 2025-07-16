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


WebUI.setText(findTestObject('Orange HRM/Login Page/field_Username'), GlobalVariable.usernameInvalid)
WebUI.setText(findTestObject('Orange HRM/Login Page/field_Password'), GlobalVariable.passwordInvalid)


WebUI.click(findTestObject('Orange HRM/Login Page/button_Login'))

// 4. Verifikasi alert error muncul
WebUI.verifyElementPresent(findTestObject('Orange HRM/Login Page/alert_Invalid Username and Password'), 5)

// 5. Verifikasi teks dari alert
WebUI.verifyElementText(findTestObject('Orange HRM/Login Page/alert_Invalid Username and Password'), 'Invalid credentials')

// 6. Verifikasi tombol login masih bisa diklik
WebUI.verifyElementClickable(findTestObject('Orange HRM/Login Page/button_Login'))

// 7. Verifikasi tetap di halaman login
WebUI.verifyMatch(WebUI.getUrl(), GlobalVariable.baseURL, false)

//// 8. Verifikasi input username masih terisi dengan value
//WebUI.verifyElementAttributeValue(findTestObject('Orange HRM/Login Page/field_Username'), 'value', GlobalVariable.usernameInvalid, 3)

// 9. Verifikasi field password tetap bertipe password
WebUI.verifyElementAttributeValue(findTestObject('Orange HRM/Login Page/field_Password'), 'type', 'password', 3)

// 10. Verifikasi bahwa link 'Forgot your password?' masih ada
WebUI.verifyTextPresent('Forgot your password?', false)

// (Opsional) Log manual status
KeywordUtil.logInfo('Semua verifikasi login invalid selesai.')


