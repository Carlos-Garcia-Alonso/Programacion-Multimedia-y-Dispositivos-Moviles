package com.carlosgarciaalonso.wrestlingapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
//**La paleta de colores es orientativa, aún es pronto para definir un estilo sólido**
private val DarkColorScheme = darkColorScheme(
    primary = BotonesD,
    secondary = TextoBotonesD,
    tertiary = TituloD,
    surface = CajaTextoD,
    onSurface = TextoCajaTextoD,
    background = FondoD,
    primaryContainer = FondoTopBarD

)

private val LightColorScheme = lightColorScheme(
    primary = BotonesL,
    onPrimary = TextoBotonesL,
    background = FondoL,
    surface = CajaTextoL,
    onSurface = TextoCajaTextoL,
    tertiary = TituloL,
    primaryContainer = FondoTopBarL

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun WrestlingAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //Si se activa esto, la aplicación adquiere los colores del tema del teléfono
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}