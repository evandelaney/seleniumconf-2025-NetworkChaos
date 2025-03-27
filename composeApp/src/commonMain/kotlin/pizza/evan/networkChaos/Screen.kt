package pizza.evan.networkChaos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Web
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val testTag: String,
    val icon: ImageVector
) {
    data object NetworkStatus : Screen("network_status", "Network", "network", Icons.Filled.NetworkCheck)
    data object HttpRequest : Screen("http_request", "HTTP Request", "httpRequest", Icons.Filled.Web)
}
