package com.example.gastronova.ui.theme

import Olive200
import Olive600
import Terracotta
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Olive600,
    secondary = Olive200,
    tertiary = Terracotta,

    background = Color(0xFFF6F3FA), // tu fondo claro del mock
    surface = Color(0xFFEAE4EF),
    onPrimary = Color.White,
    onSecondary = Color(0xFF1E1E1E),
    onTertiary = Color.White,
    onBackground = Color(0xFF1E1E1E),
    onSurface = Color(0xFF1E1E1E),
)

@Composable
fun GastroNovaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
      colorScheme = LightColorScheme,
      typography = Typography,
      content = content
    )
}