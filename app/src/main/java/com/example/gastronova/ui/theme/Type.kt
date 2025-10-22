package com.example.gastronova.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gastronova.R

// 1) Familias
val Poppins = FontFamily(
    Font(R.font.poppins_regular,  FontWeight.Normal),
    Font(R.font.poppins_medium,   FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold,     FontWeight.Bold)
)

val Inter = FontFamily(
    Font(R.font.inter_regular,  FontWeight.Normal),
    Font(R.font.inter_medium,   FontWeight.Medium),
    Font(R.font.inter_bold,     FontWeight.Bold)
)

val Manrope = FontFamily(
    Font(R.font.manrope_regular,  FontWeight.Normal),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold,     FontWeight.Bold)
)

// 2) Typography mapeada al “rol” de tu app de gastronomía
val Typography = Typography(
    // Títulos de pantallas / secciones
    displaySmall  = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold,     fontSize = 36.sp),
    headlineLarge = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold,     fontSize = 30.sp),
    headlineMedium= TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp),
    headlineSmall = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium,   fontSize = 20.sp),

    // Cards, subtítulos, ítems de lista (ej. nombre de restaurante/plato)
    titleLarge = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
    titleMedium= TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium,   fontSize = 18.sp),
    titleSmall = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium,   fontSize = 16.sp),

    // Texto de cuerpo / descripciones largas / ingredientes
    bodyLarge  = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall  = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),

    // Precios, chips, etiquetas, botones pequeños
    labelLarge = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, letterSpacing = 0.2.sp),
    labelMedium= TextStyle(fontFamily = Manrope, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, letterSpacing = 0.2.sp),
    labelSmall = TextStyle(fontFamily = Manrope, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, letterSpacing = 0.3.sp)
)
