package utils

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.ios.options.XCUITestOptions
import java.net.URI

sealed class Platform {

    abstract val driver: AppiumDriver

    abstract fun tearDown()
}

class Android : Platform() {

    override val driver: AppiumDriver
        get() = androidDriver

    private val androidOptions: UiAutomator2Options by lazy {
        UiAutomator2Options().apply {
            setAppPackage("pizza.evan.networkChaos")
            setAppActivity("pizza.evan.networkChaos.MainActivity")
        }
    }

    private val androidDriverDelegate = lazy {
        AndroidDriver(URI("http://0.0.0.0:4723").toURL(), androidOptions).apply {
            setSetting("disableIdLocatorAutocompletion", true)
        }
    }

    private val androidDriver: AndroidDriver by androidDriverDelegate

    override fun tearDown() {
        if (androidDriverDelegate.isInitialized()) {
            androidDriver.clearProxy()
            androidDriver.quit()
        }
    }
}

class Apple : Platform() {

    override val driver: AppiumDriver
        get() = appleDriver

    private val appleOptions: XCUITestOptions by lazy {
        XCUITestOptions().apply {
            setBundleId("pizza.evan.networkChaos.NetworkChaos")
            setPlatformVersion("18.3")
            setUdid("00008140-00024C9826F3001C")
        }
    }

    private val appleDriverDelegate = lazy {
        IOSDriver(URI("http://0.0.0.0:4723").toURL(), appleOptions)
    }

    private val appleDriver: IOSDriver by appleDriverDelegate

    override fun tearDown() {
        if (appleDriverDelegate.isInitialized()) {
            appleDriver.quit()
        }
    }
}