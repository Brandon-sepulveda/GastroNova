import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Color.kt
val Olive600 = Color(0xFF556B2F) // primary
val Olive200 = Color(0xFFC5D1A3) // secondary
val Terracotta = Color(0xFFD57A66) // tertiary

val DarkColorScheme = darkColorScheme(
    primary = Olive200,
    secondary = Terracotta,
    tertiary = Olive600
)

val LightColorScheme = lightColorScheme(
    primary = Olive600,
    secondary = Olive200,
    tertiary = Terracotta
)
