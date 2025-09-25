package com.qrcraft.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
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
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.QRCraftDialog
import com.qrcraft.core.presentation.designsystem.QRCraftSnackBar
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration.*
import com.qrcraft.core.presentation.designsystem.Yellow
import com.qrcraft.core.presentation.designsystem.screenConfiguration

@Composable
fun QrCodeBaseComponent(
    topBarConfig: QRCraftTopBarConfig? = null,
    showBlur: Boolean = false,
    snackBarMessage: String? = null,
    selectedOption: BottomNavigationBarOption? = null,
    infoToShow: InfoToShow = None,
    onAction: (BaseComponentAction) -> Unit = {},
    color: Color,// = MaterialTheme.colorScheme.surface,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
    content: @Composable () -> Unit,
) {
    val modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(WindowInsets.navigationBars.asPaddingValues())

    if (screenConfiguration == LANDSCAPE) {
        BaseComponentLandscape()
    } else {
        BaseComponentPortrait(
            modifier = modifier,
            topBarConfig = topBarConfig,
            showBlur = showBlur,
            snackBarMessage = snackBarMessage,
            selectedOption = selectedOption,
            infoToShow = infoToShow,
            onAction = onAction,
            content = content
        )
    }
}

//orientation
@Composable
fun BaseComponentLandscape(
    modifier: Modifier = Modifier,
) {
    
}

@Composable
fun BaseComponentPortrait(
    modifier: Modifier = Modifier,
    topBarConfig: QRCraftTopBarConfig? = null,
    showBlur: Boolean = false,
    snackBarMessage: String? = null,
    selectedOption: BottomNavigationBarOption? = null,
    infoToShow: InfoToShow = None,
    onAction: (BaseComponentAction) -> Unit = {},
    content: @Composable () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackBarMessage) {
        snackBarMessage?.let {
            snackBarHostState.showSnackbar(
                message = it,
            )
        }
    }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //top bar
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
        if (showBlur) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, end = 24.dp)//
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            //snack bar host
            /*Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                MyCustomSnackBarHost(
                    hostState = snackBarHostState
                )
            }*/
            MyCustomSnackBarHost(
                hostState = snackBarHostState
            )

            Spacer(modifier = Modifier.height(14.dp))

            //bottom nav component
            selectedOption?.let {
                QRCraftBottomNavigationBar(
                    selectedOption = it,
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

@Composable
fun MyCustomSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { data ->
        QRCraftSnackBar(
            message = data.visuals.message
        )
    }
}

@MultiDevicePreview
@Composable
private fun QrCodeBaseComponentPreview() {
    QRCraftTheme {
        QrCodeBaseComponent(
            color = MaterialTheme.colorScheme.surface,
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().background(Yellow)
                ) {  }
            }
        )
    }
}