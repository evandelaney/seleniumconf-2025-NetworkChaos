package pages

import io.appium.java_client.AppiumDriver
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.support.ui.FluentWait

class NetworkStatusPage(
    driver: AppiumDriver
) : BasePage(driver) {

    private val networkStatusIconId = "network.status.image"
    private val networkStatusTextId = "network.status.text"

    val networkStatusLabel: String
        get() = getTextFromElementById(networkStatusTextId)

    fun waitForNetworkStatus(expectedStatus: String, timeout: Duration) {
        val wait = FluentWait(driver)
            .withTimeout(timeout.toJavaDuration())
            .pollingEvery(500.milliseconds.toJavaDuration())
            .ignoring(NoSuchElementException::class.java)

        wait.until { _ ->
            networkStatusLabel == expectedStatus
        }
    }
}