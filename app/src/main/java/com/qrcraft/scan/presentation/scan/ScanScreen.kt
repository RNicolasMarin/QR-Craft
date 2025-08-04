package com.qrcraft.scan.presentation.scan

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
        AlertDialog(
            onDismissRequest = {
                showCustomDialog = false
                activity.finish()
            },
            title = { Text("Camera Permission") },
            text = {
                Text("This app needs access to your camera to take pictures.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showCustomDialog = false
                    launcher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCustomDialog = false
                    activity.finish()
                }) {
                    Text("Deny")
                }
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