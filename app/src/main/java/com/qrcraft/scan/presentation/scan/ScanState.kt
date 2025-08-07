package com.qrcraft.scan.presentation.scan

data class ScanState(
    val permissionGranted: Boolean = false,
    val showPermissionDialog: Boolean = false
)
