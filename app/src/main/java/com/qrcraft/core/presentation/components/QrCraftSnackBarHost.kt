package com.qrcraft.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.qrcraft.R
import com.qrcraft.core.presentation.components.SnackBarType.*
import com.qrcraft.core.presentation.designsystem.QRCraftSnackBar
import com.qrcraft.core.presentation.designsystem.Red
import com.qrcraft.core.presentation.designsystem.Success

@Composable
fun QrCraftSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { data ->
        val type = when (data.visuals.message) {
            DOWNLOAD_SUCCESS.text -> DOWNLOAD_SUCCESS
            DOWNLOAD_ERROR.text -> DOWNLOAD_ERROR
            else -> PERMISSION_GRANTED
        }
        QRCraftSnackBar(
            message = stringResource(type.titleRes),
            imageVector = type.imageVector,
            background = type.background
        )
    }
}

enum class SnackBarType(val text: String, val titleRes: Int, val background: Color, val imageVector: ImageVector) {
    DOWNLOAD_SUCCESS("DownloadSuccess", R.string.scan_result_saved, Success, Icons.Default.Check),
    DOWNLOAD_ERROR("DownloadError", R.string.scan_result_not_saved, Red, Icons.Default.Close),
    PERMISSION_GRANTED("PermissionGranted", R.string.camera_permission_snack_bar_granted, Success, Icons.Default.Check),
}