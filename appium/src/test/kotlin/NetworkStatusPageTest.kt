import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import pages.NetworkStatusPage
import utils.Apple
import utils.Android
import utils.setAirplaneMode

class NetworkStatusPageTest : BaseTest() {

    private lateinit var page: NetworkStatusPage

    @BeforeEach
    fun setUp() {
        // Comment out Android/Apple to run on specific platform.
        // Ideally this would be a Gradle argument `./gradlew -Dplatform=Apple`
        platform = Android()
//        platform = Apple()
        page = mainPage.getNetworkStatusPage()
    }

    @AfterEach
    fun tearDown() {
        driver.setAirplaneMode(false)
    }

    @Test
    fun `Toggle Airplane Mode On`() {
        driver.setAirplaneMode(false)
        page.waitForNetworkStatus("CONNECTED", 15.seconds)

        assert(page.networkStatusLabel == "CONNECTED")
    }

    @Test
    fun `Toggle Airplane Mode Off`() {
        driver.setAirplaneMode(true)
        page.waitForNetworkStatus("DISCONNECTED", 5.seconds)

        assert(page.networkStatusLabel == "DISCONNECTED")
    }
}
