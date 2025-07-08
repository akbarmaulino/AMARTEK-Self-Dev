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
import org.openqa.selenium.By

// ========== SCRIPT KAMU YANG SUDAH ADA ==========

WebUI.setText(findTestObject('Object Repository/Orange HRM/Login Page/field_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Login Page/field_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Orange HRM/Login Page/button_Login'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Orange HRM/Sidebar/sidebar_Admin'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/button_Add'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_User Role'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/select_Role Admin'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_Employee Name'),'Timothy Lewis')

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/select_Name'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_User Status'))

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/select_Status Enabled'))

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_username'), 'Mineeee')

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.setEncryptedText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/field_Confrim Password'), 'iFGeFYmXIrUhQZHvW7P22w==')

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/button_Save'))

if(WebUI.verifyElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/alert_Success Create'),10)) {
	println("Pesan Berhasil untuk Daftar User Baru Berhasil")
	String successMessage = WebUI.getText(findTestObject('Object Repository/Orange HRM/Admin Menu/Add Page/alert_Success Create'))
	println("Pesan Success: " + successMessage)
	WebUI.takeScreenshot()
	
}else {
	println("Pesan Berhasil tidak Muncul")
}

WebUI.waitForElementPresent(findTestObject('Object Repository/Orange HRM/Admin Menu/field_Search Username'), 10)

WebUI.delay(3)

WebUI.setText(findTestObject('Object Repository/Orange HRM/Admin Menu/field_Search Username'), 'Mineeee')

WebUI.click(findTestObject('Object Repository/Orange HRM/Admin Menu/button_Search'))

WebUI.delay(3)

List<WebElement> rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/table_All Rows'), 10)


// ✅ Case 1: Validasi Baris Hasil Search
//println("=== Mulai Validasi Semua Baris Tabel ===")
//println('Total baris ditemukan: ' + rows.size())
//
//for (int i = 0; i < rows.size(); i++) {
//	String username = rows[i].findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	String userRole = rows[i].findElement(By.xpath(".//div[@role='cell'][3]//div")).getText()
//	String employeeName = rows[i].findElement(By.xpath(".//div[@role='cell'][4]//div")).getText()
//	String status = rows[i].findElement(By.xpath(".//div[@role='cell'][5]//div")).getText()
//	println(" ")
//	println("Baris ke-${i + 1}: Username=${username}, UserRole=${userRole}, EmployeeName=${employeeName}, Status=${status}")
//}

//// ✅ SOAL 2: Validasi Baris Tertentu Saja
//println("=== Mulai Validasi Baris Tertentu ===")
//// Misal: Validasi hanya baris ke-1 dan ke-3 jika ada
//if (rows.size() >= 1) {
//	WebElement row1 = rows[0]
//	String username1 = row1.findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	println("Baris ke-1, Username: " + username1)
//}
//
//if (rows.size() >= 3) {
//	WebElement row3 = rows[2]
//	String username3 = row3.findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	println("Baris ke-3, Username: " + username3)
//}
//
// Atau berdasarkan nama tertentu, misalnya 'Mineeee':
println("=== Validasi Berdasarkan Username Tertentu ===")
for (int i = 0; i < rows.size(); i++) {
	String username = rows[i].findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
	if (username == 'Mineeee') {
		println("Baris dengan Username 'Mineeee' ditemukan di baris ke-${i + 1}")
	}
}

//// ✅ SOAL 3: Validasi Data Hasil Search di Tabel (Setelah Search)
//println("=== Mulai Validasi Hasil Search di Tabel ===")
//if (rows.size() > 0) {
//	for (int i = 0; i < rows.size(); i++) {
//		String username = rows[i].findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//		if (username == 'Mineeee') {
//			println("✅ Username 'Mineeee' ditemukan di hasil search (baris ke-${i + 1})")
//			break
//			// Bisa lanjut validasi kolom lain di sini
//		}else {
//			println("Username yang ditemukan adalah : " + username)
//		}
//	}
//} else {
//	println("❌ Tidak ada hasil search yang muncul di tabel.")
//}
//
//// ✅ SOAL 4: Print Semua Hasil di Tabel Setelah Search
//println("=== Print Semua Baris Hasil Search (Soal Nomor 4) ===")
//
//List<WebElement> filteredRows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Orange HRM/Admin Menu/table_All Rows'), 10)
//
//println('Jumlah baris hasil search: ' + filteredRows.size())
//
//for (int i = 0; i < filteredRows.size(); i++) {
//	String username = filteredRows[i].findElement(By.xpath(".//div[@role='cell'][2]//div")).getText()
//	String userRole = filteredRows[i].findElement(By.xpath(".//div[@role='cell'][3]//div")).getText()
//	String employeeName = filteredRows[i].findElement(By.xpath(".//div[@role='cell'][4]//div")).getText()
//	String status = filteredRows[i].findElement(By.xpath(".//div[@role='cell'][5]//div")).getText()
//	println("Hasil Search - Baris ke-${i + 1}: Username=${username}, UserRole=${userRole}, EmployeeName=${employeeName}, Status=${status}")
//}

