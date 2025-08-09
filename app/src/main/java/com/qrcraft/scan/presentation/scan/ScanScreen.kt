package com.qrcraft.scan.presentation.scan

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.ObserveAsEvents
import com.qrcraft.core.presentation.designsystem.QRCraftDialog
import com.qrcraft.core.presentation.designsystem.QRCraftSnackBar
import com.qrcraft.scan.presentation.scan.ScanAction.CustomDialogClosed
import com.qrcraft.scan.presentation.scan.ScanAction.RequestPermission
import com.qrcraft.scan.presentation.scan.ScanAction.UpdateAfterPermissionRequested
import com.qrcraft.scan.presentation.scan.ScanAction.UpdateGrantedInitially
import com.qrcraft.scan.presentation.scan.ScanEvent.*
import com.qrcraft.scan.presentation.util.hasCameraPermission
import com.qrcraft.scan.presentation.util.openAppSettings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanScreenRoot(
    viewModel: ScanViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(true) {
        viewModel.onAction(UpdateGrantedInitially(context.hasCameraPermission()))
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("QRCraft Permission", "isGranted: $isGranted")

        viewModel.onAction(
            UpdateAfterPermissionRequested(
                isGranted = isGranted,
                canRequestAgain = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)
            )
        )
    }

    val grantedMessage = stringResource(R.string.camera_permission_snack_bar_granted)

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            CloseApp -> activity.finish()

            RequestPermissionToSystem -> launcher.launch(Manifest.permission.CAMERA)

            OpenAppSettings -> context.openAppSettings()

            ShowPermissionGrantedSnackBar -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = grantedMessage,
                    )
                }
            }
        }
    }

    ScanScreen(
        snackBarHostState = snackBarHostState,
        state = viewModel.state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ScanScreen(
    snackBarHostState: SnackbarHostState,
    state: ScanState,
    onAction: (ScanAction) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.permissionGranted) {
                // ✅ Permission granted, show camera-related UI
                Text("Camera permission granted ✅")
            } else {
                // ❌ Not yet granted
                Text("Camera permission not granted ❌")
            }
        }
        MyCustomSnackBarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 96.dp)
        )
    }

    if (state.showPermissionDialog) {
        QRCraftDialog(
            title = R.string.camera_permission_dialog_title,
            text = R.string.camera_permission_dialog_message,
            confirmButton = R.string.camera_permission_dialog_grant,
            dismissButton = R.string.camera_permission_dialog_close,
            onDismissRequest = {
                onAction(CustomDialogClosed)
            },
            onConfirmButtonClick = {
                onAction(RequestPermission)
            },
            onDismissButtonClick = {
                onAction(CustomDialogClosed)
            }
        )
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