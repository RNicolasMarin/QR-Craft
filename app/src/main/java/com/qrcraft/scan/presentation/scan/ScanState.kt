package com.qrcraft.scan.presentation.scan

data class ScanState(
    val permissionGranted: Boolean? = null,
    val showPermissionDialog: Boolean = false
)
