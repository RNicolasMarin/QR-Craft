package com.qrcraft.core.presentation.designsystem

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberKeyboardVisibility(): State<Boolean> {
    val context = LocalContext.current
    val rootView = LocalView.current
    val keyboardVisibility = remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardVisibility.value = keypadHeight > screenHeight * 0.15
        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return keyboardVisibility
}