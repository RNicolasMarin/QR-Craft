package com.qrcraft.scan.presentation.scan

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.QRCraftDialog
import com.qrcraft.scan.presentation.util.checkCameraPermission
import com.qrcraft.scan.presentation.util.openAppSettings

@Composable
fun ScanScreenRoot() {
    val context = LocalContext.current
    val activity = context as Activity

    var showCustomDialog by remember { mutableStateOf(false) }
    var permissionGranted by remember { mutableStateOf(context.checkCameraPermission()) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("QRCraft Permission", "isGranted: $isGranted")

        when {
            isGranted -> {
                permissionGranted = true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                // Just denied (user might allow next time)
                activity.finish()
            }
            else -> {
                // "Don't ask again" was selected
                context.openAppSettings()
            }
        }
    }


    LaunchedEffect(Unit) {
        Log.d("QRCraft Permission", "isGranted: $permissionGranted")
        if (!permissionGranted) {
            showCustomDialog = true
        }
    }

    if (showCustomDialog) {
        QRCraftDialog(
            title = R.string.camera_permission_dialog_title,
            text = R.string.camera_permission_dialog_message,
            confirmButton = R.string.camera_permission_dialog_grant,
            dismissButton = R.string.camera_permission_dialog_close,
            onDismissRequest = {
                showCustomDialog = false
                activity.finish()
            },
            onConfirmButtonClick = {
                showCustomDialog = false
                launcher.launch(Manifest.permission.CAMERA)
            },
            onDismissButtonClick = {
                showCustomDialog = false
                activity.finish()
            }
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (permissionGranted) {
            // ✅ Permission granted, show camera-related UI
            Text("Camera permission granted ✅")
        } else {
            // ❌ Not yet granted
            Text("Camera permission not granted ❌")
        }
    }
}