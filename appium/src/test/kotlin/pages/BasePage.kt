package pages

import io.appium.java_client.AppiumBy
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.WebElement

open class BasePage(
    protected val driver: AppiumDriver
) {
    
    fun findElementById(id: String): WebElement {
        return driver.findElement(AppiumBy.id(id))
    }
    
    fun clickElementById(id: String) {
        findElementById(id).click()
    }
    
    fun getTextFromElementById(id: String): String {
        return findElementById(id).text
    }
}
