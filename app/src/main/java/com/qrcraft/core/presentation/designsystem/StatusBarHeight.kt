package com.qrcraft.core.presentation.designsystem

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun statusBarHeight(): Dp {
    val insets = WindowInsets.statusBars.asPaddingValues()
    return insets.calculateTopPadding()
}