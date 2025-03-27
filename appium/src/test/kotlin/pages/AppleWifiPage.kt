package pages

import io.appium.java_client.ios.IOSDriver
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait

class AppleWifiPage(driver: IOSDriver) : BasePage(driver) {

    private val wifiSwitchSelector = By.xpath("//XCUIElementTypeSwitch[@name=\"Wi‑Fi\"]")

    private fun getWifiSwitch(
        timeoutSeconds: Int = 5,
        pollingMilliseconds: Int = 200
    ): WebElement =
        // Find the Wi-Fi switch
        FluentWait(driver)
            .withTimeout(timeoutSeconds.seconds.toJavaDuration())
            .pollingEvery(pollingMilliseconds.milliseconds.toJavaDuration())
            .ignoring(NoSuchElementException::class.java)
            .until(ExpectedConditions.presenceOfElementLocated(wifiSwitchSelector))

    fun toggleWifi(on: Boolean) {
        val wifiSwitch = getWifiSwitch()
        // Get the current value of the Wi-Fi switch
        val wifiSwitchValue = wifiSwitch.getAttribute("value")

        if ((!on && wifiSwitchValue == "1") || (on && wifiSwitchValue == "0")) {
            wifiSwitch.click()
            getWifiSwitch()
        }
    }
}