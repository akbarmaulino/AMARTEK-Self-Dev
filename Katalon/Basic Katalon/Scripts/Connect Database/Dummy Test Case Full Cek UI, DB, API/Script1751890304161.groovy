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
import com.database.PostgreDBUtils as DBUtils
import com.kms.katalon.core.util.KeywordUtil

// Step 1️⃣: Register User Baru via UI
WebUI.openBrowser('')
WebUI.navigateToUrl('https://your-app.com/register')
WebUI.setText(findTestObject('Object Repository/input_email'), 'userbaru@mail.com')
WebUI.setText(findTestObject('Object Repository/input_password'), 'password123')
WebUI.click(findTestObject('Object Repository/btn_register'))
WebUI.verifyTextPresent('Registrasi berhasil', false)
KeywordUtil.logInfo "✅ Sukses daftar user baru di UI"

// Step 2️⃣: Cek Database PostgreSQL
try {
	DBUtils.connectDB('localhost', '5432', 'MyDatabase', 'postgres', 'P@ssw0rd')
	def rs = DBUtils.executeQuery("SELECT * FROM users WHERE email = 'userbaru@mail.com'")
	if (rs.next()) {
		KeywordUtil.logInfo "✅ User ditemukan di database: ${rs.getString('email')}"
	} else {
		KeywordUtil.logInfo "❌ User TIDAK ditemukan di database!"
	}
} catch (Exception e) {
	KeywordUtil.logInfo "❌ ERROR DB: " + e.message
} finally {
	DBUtils.closeDB()
}

// Step 3️⃣: Cek Data Lewat API
def response = WS.sendRequest(findTestObject('Object Repository/API/GetUser', [('email') : 'userbaru@mail.com']))
WS.verifyResponseStatusCode(response, 200)
WS.verifyElementPropertyValue(response, 'email', 'userbaru@mail.com')
KeywordUtil.logInfo "✅ Data user valid lewat API"

// Step 4️⃣: Tutup Browser
WebUI.closeBrowser()


