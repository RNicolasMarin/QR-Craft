package com.qrcraft.scan.presentation.scan

import com.qrcraft.scan.domain.QrType

sealed interface ScanEvent {

    data object RequestPermissionToSystem : ScanEvent

    data object CloseApp : ScanEvent

    data object OpenAppSettings : ScanEvent

    data object ShowPermissionGrantedSnackBar : ScanEvent

    data class GoToScanResult(
        val content: QrType
    ) : ScanEvent

}