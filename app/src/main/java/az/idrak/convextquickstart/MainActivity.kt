package az.idrak.convextquickstart

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import az.idrak.convextquickstart.ui.theme.ConvextQuickstartTheme
import dev.convex.android.ConvexClient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvextQuickstartTheme {
                // A surface container using the 'background' color from the theme
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Words(
                        client = ConvexClient("https://keen-reindeer-202.convex.cloud"),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Words(client: ConvexClient, modifier: Modifier = Modifier) {
    var words: List<Word> by remember { mutableStateOf(listOf()) }
    LaunchedEffect(key1 = "launch") {
        client.subscribe<List<Word>>("word:getWords").collect { result ->
            result.onSuccess { remoteWords ->
                words = remoteWords
                Log.d("OnSuccess", "Loaded "+words.size)
            }

            result.onFailure { e ->
                e.localizedMessage?.let { Log.e("ERRORE", it) }
                e.printStackTrace()
            }
        }
    }



    LazyColumn (
        modifier = modifier
    ) {

        items(words) { w ->
            Log.i("LAZYM", "DEYIL "+ w.value)
            Text(color = Color.Red, text = "Value: ${w.value}, Direction: ${w.starts}")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConvextQuickstartTheme {
        Greeting("Android")
    }
}