import io.appium.java_client.AppiumDriver
import org.junit.jupiter.api.AfterEach
import pages.MainPage
import kotlin.properties.Delegates.observable
import utils.Android
import utils.Platform

abstract class BaseTest {

    protected var platform: Platform? by observable(null) { _, oldValue, newValue ->
        if (newValue != oldValue && newValue != null) {
            driver = newValue.driver
            mainPage = MainPage(driver)
        }
    }

    protected lateinit var driver: AppiumDriver
        private set

    protected lateinit var mainPage: MainPage
        private set

    @AfterEach
    fun teardown() {
        platform?.tearDown()
    }
}
