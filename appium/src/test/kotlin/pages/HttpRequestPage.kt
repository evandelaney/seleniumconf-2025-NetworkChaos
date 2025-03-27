package pages

import io.appium.java_client.AppiumDriver

class HttpRequestPage(
    driver: AppiumDriver
) : BasePage(driver) {

    private val httpRequestButtonId = "http.request.button"
    private val httpStatusTextId = "http.status.text"

    val httpStatus: String
        get() = getTextFromElementById(httpStatusTextId)

    fun clickHttpRequestButton() {
        clickElementById(httpRequestButtonId)
    }
}
