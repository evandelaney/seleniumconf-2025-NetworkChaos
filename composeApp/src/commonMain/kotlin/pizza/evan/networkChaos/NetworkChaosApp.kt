package pizza.evan.networkChaos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun NetworkChaosApp(networkMonitorFactory: NetworkMonitorFactory) {
    val screens = listOf(Screen.NetworkStatus, Screen.HttpRequest)
    var currentScreen by remember { mutableStateOf<Screen>(Screen.NetworkStatus) }
    val networkMonitor = networkMonitorFactory.create()

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        modifier = Modifier.testTag("navigation.barItem.${screen.testTag}"),
                        onClick = { currentScreen = screen },
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Screen.NetworkStatus -> NetworkStatusScreen(networkMonitor)
                Screen.HttpRequest -> HttpRequestScreen()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            networkMonitor.cleanup()
        }
    }
}