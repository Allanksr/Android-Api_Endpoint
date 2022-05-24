package allanksr.com.api_endpoint.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    background = Color(0xFF1F1F1F),
    onBackground = Color(0xFFE0E0E0),

    primary = Color(0xFF1E581C),
    primaryVariant = Color(0xFF888A79),
    onPrimary = Color(0xFF5C5E52),

    secondary = Color(0xFFA0A766),
    secondaryVariant = Color(0xFF707541),
    onSecondary = Color(0xFF444726),
)

private val LightColorPalette = lightColors(
    background = Color(0xFFDBDADA),
    onBackground = Color(0xFF242424),

    primary = Color(0xFFA3A591),
    primaryVariant = Color(0xFF888A79),
    onPrimary = Color(0xFF5C5E52),

    secondary = Color(0xFFA0A766),
    secondaryVariant = Color(0xFF707541),
    onSecondary = Color(0xFF444726),
)

@Composable
fun ApiEndpointTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}