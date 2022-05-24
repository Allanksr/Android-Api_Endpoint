package allanksr.com.api_endpoint.ui

import allanksr.com.api_endpoint.R
import allanksr.com.api_endpoint.repositories.DefaultPromotionalRepository
import allanksr.com.api_endpoint.ui.nav_graph.NavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import allanksr.com.api_endpoint.ui.theme.ApiEndpointTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var defaultPromotionalRepository: DefaultPromotionalRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiEndpointTheme(
                darkTheme = true,
                content = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background,
                        content = {
                            NavGraph()
                        }
                    )
                }
            )
        }
    }
}



/*
@Composable
fun Greeting(name: String) {
    Text(
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 10.dp,
                end = 10.dp,
                bottom = 10.dp,
            ),
        text = "Hello $name!",
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onBackground
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApiEndpointTheme(
        darkTheme = false
    ) {
        Greeting(name = stringResource(R.string.app_name))
    }
}
 */