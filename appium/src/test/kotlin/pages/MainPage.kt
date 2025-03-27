package pages

import io.appium.java_client.AppiumDriver

class MainPage(
    driver: AppiumDriver
) : BasePage(driver) {

    private val networkStatusNavigation = "navigation.barItem.network"
    private val httpRequestNavigation = "navigation.barItem.httpRequest"

    fun getNetworkStatusPage(): NetworkStatusPage {
        clickElementById(networkStatusNavigation)
        return NetworkStatusPage(driver)
    }

    fun getHttpRequestPage(): HttpRequestPage {
        clickElementById(httpRequestNavigation)
        return HttpRequestPage(driver)
    }
}

