package pizza.evan.networkChaos

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Network.*
import platform.darwin.dispatch_get_main_queue

class AppleNetworkMonitor : NetworkMonitor {

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val monitor = nw_path_monitor_create()

    init {
        val queue = dispatch_get_main_queue()
        nw_path_monitor_set_queue(monitor, queue)
        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = nw_path_get_status(path)
            _isConnected.value = when(status) {
                nw_path_status_satisfied -> true
                else -> false
            }
        }
        nw_path_monitor_start(monitor)
    }

    override fun cleanup() {
        nw_path_monitor_cancel(monitor)
    }
}

class AppleNetworkMonitorFactory : NetworkMonitorFactory {
    override fun create(): NetworkMonitor {
        return AppleNetworkMonitor()
    }
}
