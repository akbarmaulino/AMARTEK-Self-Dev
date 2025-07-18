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
TestData data = findTestData('LoginCredential')

for (int i = 1; i <= data.getRowNumbers(); i++) {
    String username = data.getValue('username', i)

    String password = data.getValue('password', i)

    WebUI.setText(findTestObject('Orange HRM/Login Page/field_Username'), username)

    WebUI.setText(findTestObject('Orange HRM/Login Page/field_Password'), password)

    WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

    WebUI.delay(5)

    String currentUrl = WebUI.getUrl()

    if (currentUrl.contains('/dashboard/index')) {
        KeywordUtil.logInfo("Data Ke-$i: Sukses Masuk ke Main Page dengan Username: $username")
    } else {
        KeywordUtil.logInfo("Data Ke-$i: Gagal Masuk ke Main Page / Gagal Login dengan Username: $username")
    }
}

