package pizza.evan.networkChaos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = AndroidNetworkMonitorFactory().also { it.initialize(this) }
        setContent {
            Box(
                modifier = Modifier.semantics { testTagsAsResourceId = true }
            ) {
                NetworkChaosApp(factory)
            }
        }
    }
}

