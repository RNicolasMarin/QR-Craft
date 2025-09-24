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
        //widthDp > heightDp && heightDp > 600 -> TABLET_LANDSCAPE
        widthDp > heightDp -> LANDSCAPE
        //widthDp < 840 && heightDp < 900 -> PHONE_PORTRAIT
        widthDp < 600 -> PHONE_PORTRAIT
        else -> TABLET_PORTRAIT
    }

    val dimens = when (screenConfiguration) {
        PHONE_PORTRAIT -> dimensPhonePortrait
        TABLET_PORTRAIT -> dimensTabletPortrait
        LANDSCAPE -> dimensPhonePortrait//check again
    }

    ProvideDimens(dimens) {
        ProvideScreenConfiguration(screenConfiguration) {
            MaterialTheme(
                colorScheme = ColorScheme,
                typography = Typography,
                content = content
            )
        }
    }
}