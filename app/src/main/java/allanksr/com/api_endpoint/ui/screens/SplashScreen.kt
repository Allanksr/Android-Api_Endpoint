package allanksr.com.api_endpoint.ui.screens

import allanksr.com.api_endpoint.ui.util_composable.GlowIndicatorLoader
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import allanksr.com.api_endpoint.R
import allanksr.com.api_endpoint.common.TestTags
import allanksr.com.api_endpoint.ui.nav_graph.Route
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    val refreshState = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        refreshState.value = true
        delay(5000)
        navController.popBackStack()
        navController.navigate(Route.MainScreen.route)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                content = {
                    Text(
                        modifier = Modifier.clickable {
                                navController.popBackStack()
                                navController.navigate(Route.MainScreen.route)
                            }
                            .testTag(TestTags.APP_NAME),
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 30.sp,
                            color = MaterialTheme.colors.onBackground
                        ),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.android_font_101)),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 45.sp,
                                    color = MaterialTheme.colors.onBackground
                                )
                            ) {
                                append("${stringResource(id = R.string.api)} ")
                            }
                            append(stringResource(id = R.string.endpoint))
                        }
                    )
                }
            )

            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                content = {
                    GlowIndicatorLoader(
                        modifier = Modifier.semantics {
                            contentDescription = "${refreshState.value}"
                        },
                        refreshState = refreshState,
                        refreshTriggerDistance = 8.dp
                    )
                }
            )
        }
    )
}