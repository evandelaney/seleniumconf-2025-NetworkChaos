import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import pages.NetworkStatusPage
import utils.Apple
import utils.setAirplaneMode

class NetworkStatusPageTest : BaseTest() {

    private lateinit var page: NetworkStatusPage

    @BeforeEach
    fun setUp() {
//        platform = Android()
        platform = Apple()
        page = mainPage.getNetworkStatusPage()
    }

    @AfterEach
    fun tearDown() {
        driver.setAirplaneMode(false)
    }

    @Test
    fun `Toggle Airplane Mode On`() = runBlocking {
        driver.setAirplaneMode(false)
        page.waitForNetworkStatus("CONNECTED", 15.seconds)

        assert(page.networkStatusLabel == "CONNECTED")
    }

    @Test
    fun `Toggle Airplane Mode Off`() = runBlocking {
        driver.setAirplaneMode(true)
        page.waitForNetworkStatus("DISCONNECTED", 5.seconds)

        assert(page.networkStatusLabel == "DISCONNECTED")
    }
}
