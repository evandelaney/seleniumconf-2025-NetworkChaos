package pizza.evan.networkChaos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch


@Composable
fun HttpRequestScreen() {
    var statusCode by remember { mutableStateOf<Int?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val client = remember { HttpClient(CIO) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = client.get("https://emojihub.yurace.pro/api/random")
                        statusCode = response.status.value
                    } catch (e: Exception) {
                        println(e)
                        statusCode = 500
                    }
                }
            },
            modifier = Modifier.padding(16.dp).testTag("http.request.button")
        ) {
            Text("Check Status")
        }

        statusCode?.let { code ->
            Text(
                text = "Status: $code",
                style = MaterialTheme.typography.headlineMedium,
                color = if (code == 200) Color.Green else Color.Red,
                modifier = Modifier.padding(16.dp).testTag("http.status.text")
            )
        }
    }
}