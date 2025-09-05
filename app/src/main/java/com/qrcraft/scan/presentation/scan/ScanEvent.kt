package com.qrcraft.scan.presentation.scan

sealed interface ScanEvent {

    data object RequestPermissionToSystem : ScanEvent

    data object CloseApp : ScanEvent

    data object OpenAppSettings : ScanEvent

    data object ShowPermissionGrantedSnackBar : ScanEvent

    data class GoToScanResult(
        val qrCodeId: Int
    ) : ScanEvent

}