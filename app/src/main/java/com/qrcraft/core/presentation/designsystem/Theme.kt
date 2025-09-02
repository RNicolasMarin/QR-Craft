package com.qrcraft.core.presentation.designsystem

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration.*

private val ColorScheme = lightColorScheme(
    primary = Yellow,
    surface = Grey1,
    surfaceContainerHigh = White,
    onSurface = Black,
    onSurfaceVariant = Grey2,
    surfaceContainerHighest = White,
    error = Red,
    outline = Outline
)

@Composable
fun QRCraftTheme(
    content: @Composable () -> Unit
) {

    val configuration = LocalConfiguration.current
    val heightDp = configuration.screenHeightDp
    val widthDp = configuration.screenWidthDp

    Log.d("ScreenSize", "widthDp = $widthDp, heightDp = $heightDp")

    val screenConfiguration = when {
        widthDp < 600 -> MOBILE_DEVICES
        else -> WIDER_SCREEN
    }

    val dimens = when (screenConfiguration) {
        MOBILE_DEVICES -> dimensMobile
        WIDER_SCREEN -> dimensWider
    }

    ProvideDimens(dimens) {
        MaterialTheme(
            colorScheme = ColorScheme,
            typography = Typography,
            content = content
        )
    }

}