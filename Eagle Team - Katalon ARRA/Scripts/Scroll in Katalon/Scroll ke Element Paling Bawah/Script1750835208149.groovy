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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement


WebUI.openBrowser('')
WebUI.navigateToUrl('https://the-internet.herokuapp.com/')


WebUI.click(findTestObject('Object Repository/New Folder/Page_The Internet/a_WYSIWYG Editor'))


WebUI.waitForPageLoad(10)

List<WebElement> paragraphsBefore = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/New Folder/Page_The Internet/paragraph'), 5)
int countBefore = paragraphsBefore.size()
println("Jumlah paragraf sebelum scroll: " + countBefore)

int maxScroll = 5
int currentScroll = 0
int countAfter = countBefore

while (currentScroll < maxScroll) {
    println("Melakukan scroll ke-" + (currentScroll + 1))
	//Scroll Kebagian paling bawah di halaman
    WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null)
    WebUI.delay(5)

    countAfter = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/New Folder/Page_The Internet/paragraph'), 5).size()
    println("Jumlah paragraf sekarang: " + countAfter)

    currentScroll++
}
if (countAfter > countBefore) {
    println("✅ Paragraf berhasil bertambah: dari $countBefore menjadi $countAfter")
} else {
    println("❌ Paragraf tidak bertambah setelah scroll.")
    WebUI.takeScreenshot()
    WebUI.comment("Gagal memuat paragraf baru. Silakan cek lazy load-nya.")
}

WebUI.closeBrowser()
