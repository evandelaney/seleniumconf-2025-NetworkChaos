import io.appium.java_client.android.AndroidDriver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pages.HttpRequestPage
import utils.Android
import utils.NetworkUtils
import java.time.Duration
import utils.clearProxy
import utils.setProxy

class HttpRequestPageTest : BaseTest() {

    private lateinit var page: HttpRequestPage
    private var mitmProcess: Process? = null

    @BeforeEach
    fun setUp() {
        platform = Android()
        page = mainPage.getHttpRequestPage()
    }

    @AfterEach
    fun cleanUpProxy() {
        mitmProcess?.let {
            NetworkUtils.stopMitmproxy(it)
            mitmProcess = null
        }
    }

    @Test
    fun `API response without mitmproxy returning 200`() {
        page.clickHttpRequestButton()
        assert(page.httpStatus.contains("Status: 200"))
    }

    @Test
    fun `API response with mitmproxy returning 404`() {
        try {
            mitmProcess = NetworkUtils.startMitmproxyWithErrorCode(404)

            // Wait for mitmproxy to initialize
            Thread.sleep(1000)
            driver.setProxy("172.20.96.87")

            page.clickHttpRequestButton()

            assert(page.httpStatus.contains("Status: 404"))
        } catch (e: Exception) {
            throw e
        }
    }

    @Test
    fun `API response with mitmproxy returning 500`() {
        try {
            mitmProcess = NetworkUtils.startMitmproxyWithErrorCode(500)

            // Wait for mitmproxy to initialize
            Thread.sleep(1000)
            driver.setProxy("172.20.96.87")

            page.clickHttpRequestButton()
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5))
            assert(page.httpStatus.contains("Status: 500"))
        } catch (e: Exception) {
            throw e
        }
    }
}
