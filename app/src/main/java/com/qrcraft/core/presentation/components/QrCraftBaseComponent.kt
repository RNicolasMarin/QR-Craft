package com.qrcraft.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.BaseComponentAction.*
import com.qrcraft.core.presentation.components.InfoToShow.*
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.QRCraftDialog
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration.*
import com.qrcraft.core.presentation.designsystem.Yellow
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.core.presentation.designsystem.screenConfiguration
import kotlinx.coroutines.delay

@Composable
fun QrCraftBaseComponent(
    color: Color,
    topBarConfig: QRCraftTopBarConfig? = null,
    showBlur: Boolean = false,
    snackBarMessage: String? = null,
    selectedOption: BottomNavigationBarOption? = null,
    infoToShow: InfoToShow = None,
    onAction: (BaseComponentAction) -> Unit = {},
    dimens: DimensTopBar = MaterialTheme.dimen.topBar,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
    content: @Composable () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackBarMessage) {
        snackBarMessage?.let {
            snackBarHostState.showSnackbar(
                message = it,
            )
            delay(100)
            onAction(SnackBarClearMessage)
        }
    }

    val modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(WindowInsets.navigationBars.asPaddingValues())

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //top bar //check orientation sizes
            topBarConfig?.let {
                QRCraftTopBar(
                    config = it,
                    modifier = Modifier.fillMaxWidth(),
                    onAction = onAction
                )
            }

            content()
        }

        //bottom nav blur component
        if (showBlur && (screenConfiguration == PHONE_PORTRAIT || screenConfiguration == TABLET_PORTRAIT)) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = dimens.paddingStart, end = dimens.paddingEnd)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFEDF2F5).copy(alpha = 0f),  // top transparent
                                    Color(0xFFEDF2F5)                    // bottom solid
                                )
                            )
                        )
                )
            }
        }

        if (screenConfiguration == LANDSCAPE && selectedOption != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                QRCraftBottomNavigationBar(
                    selectedOption = selectedOption,
                    onAction = onAction
                )

                Spacer(modifier = Modifier.width(14.dp))
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            //snack bar host
            QrCraftSnackBarHost(
                hostState = snackBarHostState
            )

            Spacer(modifier = Modifier.height(14.dp))

            //bottom nav component
            if (screenConfiguration != LANDSCAPE && selectedOption != null) {
                QRCraftBottomNavigationBar(
                    selectedOption = selectedOption,
                    onAction = onAction
                )

                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            when (infoToShow) {
                None -> Unit
                is RequestPermission -> {
                    QRCraftDialog(
                        title = infoToShow.title,
                        text = infoToShow.text,
                        confirmButton = infoToShow.confirmButton,
                        dismissButton = infoToShow.dismissButton,
                        onDismissRequest = {
                            onAction(DialogOnClosed)
                        },
                        onConfirmButtonClick = {
                            onAction(DialogOnConfirm)
                        },
                        onDismissButtonClick = {
                            onAction(DialogOnClosed)
                        }
                    )
                }
                Loading -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 4.dp,
                            color = OnOverlay
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(R.string.scanning_loading),
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnOverlay,
                        )
                    }
                }
                Error -> {
                    QRCraftDialog(
                        title = R.string.scanning_no_qr_found,
                        icon = R.drawable.alert_triangle
                    ) {
                        onAction(DialogOnErrorClosed)
                    }
                }
            }
        }
    }
}

@MultiDevicePreview
@Composable
private fun QrCodeBaseComponentPreview() {
    QRCraftTheme {
        QrCraftBaseComponent(
            color = MaterialTheme.colorScheme.surface,
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().background(Yellow)
                ) {  }
            }
        )
    }
}