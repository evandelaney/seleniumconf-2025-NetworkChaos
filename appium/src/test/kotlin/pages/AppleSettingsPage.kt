package pages

import io.appium.java_client.ios.IOSDriver
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait

class AppleSettingsPage(driver: IOSDriver) : BasePage(driver) {

    private val wifiCellSelector = By.id("com.apple.settings.wifi")

    private fun getWifiCell(
        timeoutSeconds: Int = 5,
        pollingMilliseconds: Int = 200
    ): WebElement =
        // Find the Wi-Fi cell
        FluentWait(driver)
            .withTimeout(timeoutSeconds.seconds.toJavaDuration())
            .pollingEvery(pollingMilliseconds.milliseconds.toJavaDuration())
            .ignoring(NoSuchElementException::class.java)
            .until(ExpectedConditions.presenceOfElementLocated(wifiCellSelector))

    fun navigateToWifiSettings(): AppleWifiPage {
        getWifiCell().click()
        return AppleWifiPage(driver as IOSDriver)
    }
}