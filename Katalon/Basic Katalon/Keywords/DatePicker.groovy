import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.By


class DatePicker {

	@Keyword
	def void selectStaticDateCombined(String tanggal) {
		// Format input: dd-MM-yyyy
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
		LocalDate targetDate = LocalDate.parse(tanggal, formatter)

		int targetYear = targetDate.getYear()
		int targetMonth = targetDate.getMonthValue()
		int targetDay = targetDate.getDayOfMonth()

		WebUI.comment("Target: ${targetDay}-${targetMonth}-${targetYear}")

		// Scroll ke bawah agar datepicker terlihat
		WebUI.executeJavaScript("window.scrollBy(0, 1500)", null)
		WebUI.delay(1)

		// === STEP 1: Klik header untuk buka pemilihan tahun ===
		TestObject headerBtn = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//p[text()='Static variant']/following::div[contains(@class,'MuiPickersCalendarHeader-labelContainer')]//button")
		WebUI.click(headerBtn)

		// === STEP 2: Pilih tahun ===
		TestObject yearBtn = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'MuiYearCalendar-root')]//button[text()='" + targetYear + "']")
		WebUI.scrollToElement(yearBtn, 2)
		WebUI.click(yearBtn)
		WebUI.delay(1)

		// === STEP 3: Navigasi bulan seperti biasa ===
		TestObject calendarLabel = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//p[text()='Static variant']/following::div[contains(@class,'MuiPickersCalendarHeader-label')][1]")

		String currentLabel = WebUI.getText(calendarLabel)
		String[] parts = currentLabel.split(" ")
		int currentMonth = Month.valueOf(parts[0].toUpperCase()).getValue()
		int currentYear = Integer.parseInt(parts[1])

		while (currentMonth != targetMonth) {
			if (currentMonth > targetMonth) {
				TestObject prevBtn = new TestObject().addProperty("xpath", ConditionType.EQUALS,
						"//p[text()='Static variant']/following::button[@aria-label='Previous month']")
				WebUI.click(prevBtn)
			} else {
				TestObject nextBtn = new TestObject().addProperty("xpath", ConditionType.EQUALS,
						"//p[text()='Static variant']/following::button[@aria-label='Next month']")
				WebUI.click(nextBtn)
			}

			WebUI.delay(0.3)
			currentLabel = WebUI.getText(calendarLabel)
			parts = currentLabel.split(" ")
			currentMonth = Month.valueOf(parts[0].toUpperCase()).getValue()
		}

		// === STEP 4: Klik tanggal ===
		TestObject dayButton = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"(//p[text()='Static variant']/following::div[contains(@class,'MuiDateCalendar-root')]//button[.='${targetDay}'])[1]")
		WebUI.click(dayButton)

		WebUI.comment("Tanggal $tanggal dipilih")
	}

	@Keyword
	def String getStaticDatePickerValue() {
		// Ambil tanggal (day)
		TestObject selectedDate = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//p[text()='Static variant']/following::button[@aria-selected='true']")
		String selectedDay = WebUI.getText(selectedDate)

		// Ambil bulan dan tahun
		TestObject monthYear = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//p[text()='Static variant']/following::div[contains(@class,'MuiPickersCalendarHeader-label')]")
		String monthYearText = WebUI.getText(monthYear) // e.g., "April 2022"

		// Ubah bulan ke format angka
		Map<String, String> monthMap = [
			"January":"01", "February":"02", "March":"03", "April":"04", "May":"05", "June":"06",
			"July":"07", "August":"08", "September":"09", "October":"10", "November":"11", "December":"12"
		]

		String[] parts = monthYearText.split(" ")
		String bulan = monthMap[parts[0]]
		String tahun = parts[1]

		// Gabungkan jadi dd/MM/yyyy
		String tanggalLengkap = "${selectedDay.padLeft(2, '0')}/${bulan}/${tahun}"

		return tanggalLengkap
	}


	enum Month {
		JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6),
		JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12)

		final int value
		Month(int value) {
			this.value = value
		}

		static Month valueOf(int month) {
			return values().find { it.value == month }
		}
	}
}
