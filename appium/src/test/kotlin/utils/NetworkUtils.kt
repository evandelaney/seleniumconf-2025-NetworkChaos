package utils

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.connection.ConnectionStateBuilder
import io.appium.java_client.ios.IOSDriver
import pages.AppleSettingsPage
import java.io.File
import java.io.IOException

object NetworkUtils {

    fun startMitmproxyWithErrorCode(statusCode: Int, port: Int = 8080): Process {
        val scriptContent = """
            from mitmproxy import http
            
            class ApiResponse:
                def response(self, flow: http.HTTPFlow) -> None:
                    if "emojihub.yurace.pro" in flow.request.pretty_host:
                        flow.response.status_code = $statusCode
                        flow.response.text = "$statusCode Error - Modified by mitmproxy"
                        flow.response.headers["Content-Type"] = "text/plain"
            
            addons = [
                ApiResponse()
            ]
        """.trimIndent()

        val tempFile = createTempPythonFile(scriptContent)

        return ProcessBuilder(
            "mitmdump",
            "--listen-port", port.toString(),
            "-s", tempFile.absolutePath
        ).redirectErrorStream(true).start()
    }

    fun stopMitmproxy(process: Process) {
        process.destroy()
        process.waitFor()
    }

    private fun createTempPythonFile(content: String): File {
        val tempFile = File.createTempFile("mitmproxy_script_", ".py")
        tempFile.writeText(content)
        tempFile.deleteOnExit()
        return tempFile
    }
}

fun AppiumDriver.setAirplaneMode(on: Boolean) {
    when (this) {
        is AndroidDriver -> setAndroidAirplaneMode(on)
        is IOSDriver -> setAppleAirplaneMode(on)
    }
}

fun AppiumDriver.setProxy(
    host: String = "127.0.0.1",
    port: Int = 8080
) {
    when (this) {
        is AndroidDriver -> setAndroidProxy(host, port)
        is IOSDriver -> TODO("Not implemented")
    }
}

fun AppiumDriver.clearProxy() {
    when (this) {
        is AndroidDriver -> clearAndroidProxy()
        is IOSDriver -> TODO("Not implemented")
    }
}

private fun AndroidDriver.setAndroidProxy(host: String, port: Int) {
    executeScript(
        "mobile: shell", mapOf(
            "command" to "settings put global http_proxy $host:$port"
        )
    )
}

private fun AndroidDriver.clearAndroidProxy() {
    executeScript(
        "mobile: shell", mapOf(
            "command" to "settings put global http_proxy :0"
        )
    )
}

private fun AndroidDriver.setAndroidAirplaneMode(on: Boolean) {
    connection = ConnectionStateBuilder().apply {
        if (on) {
            withAirplaneModeEnabled()
        } else {
            withAirplaneModeDisabled().withWiFiEnabled()
        }
    }.build()
}

private fun IOSDriver.setAppleAirplaneMode(on: Boolean) {
    val bundleId = capabilities.getCapability("appium:bundleId") as String?

    // Kill Settings app so we're starting from a known good state
    terminateApp("com.apple.Preferences")

    // Launch Settings app
    activateApp("com.apple.Preferences")

    val settingsPage = AppleSettingsPage(this)
    val wifiPage = settingsPage.navigateToWifiSettings()
    wifiPage.toggleWifi(!on)

    // Launch the app under test
    activateApp(bundleId)
}

