package allanksr.com.api_endpoint.ui

import allanksr.com.api_endpoint.common.TestTags
import allanksr.com.api_endpoint.ui.nav_graph.NavGraph
import allanksr.com.api_endpoint.ui.theme.ApiEndpointTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
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


    @Test
    fun goMainScreen_andMakeRequest() = runTest {
        composeRule.onNodeWithTag(TestTags.APP_NAME).performClick()
        composeRule.onNodeWithTag(TestTags.MAKE_REQUEST).performClick()
    }


}