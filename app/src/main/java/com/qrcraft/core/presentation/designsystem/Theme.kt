package com.qrcraft.core.presentation.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = Yellow,
    surface = Grey1,
    surfaceContainerHigh = White,
    onSurface = Black,
    onSurfaceVariant = Grey2,
    surfaceContainerHighest = White,
    error = Red
)

@Composable
fun QRCraftTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}