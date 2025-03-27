package pizza.evan.networkChaos

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isConnected: StateFlow<Boolean>
    fun cleanup()
}

interface NetworkMonitorFactory {
    fun create(): NetworkMonitor
}

