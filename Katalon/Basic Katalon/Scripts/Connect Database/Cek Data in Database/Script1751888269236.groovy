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

try {
    // Coba koneksi ke database
    DBUtils.connectDB('localhost', '5432', 'MyDatabase', 'postgres', 'P@ssw0rd')
    println "Koneksi berhasil! Password benar."

    // Query SELECT (contoh cek user)
    def rs = DBUtils.executeQuery("SELECT * FROM users WHERE name = 'Alice'")
    if (rs.next()) {
		println("Nama : " + rs.getString('name') + " Berhasil Ditemukan")
	} else {
        println "Data tidak ditemukan"
    }

} catch (Exception e) {
    println "Koneksi gagal! Password salah atau DB tidak bisa diakses."
    println e.message
} finally {
    try {
        DBUtils.closeDB()
    } catch (Exception e) {
        println "Tidak bisa menutup koneksi: " + e.message
    }
}
