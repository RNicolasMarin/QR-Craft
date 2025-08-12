package com.qrcraft.scan.presentation.scan

import com.qrcraft.scan.presentation.scan.ScanInfoToShow.NONE

data class ScanState(
    val permissionGranted: Boolean? = null,
    val infoToShow: ScanInfoToShow = NONE,
    val isScanning: Boolean = false,
    val content: String = ""
)

enum class ScanInfoToShow {
    NONE,
    REQUEST_PERMISSION,
    LOADING,
    NO_QR_FOUND
}
