package com.qrcraft.scan.presentation.scan

sealed class ScanEvent {

    data object RequestPermissionToSystem : ScanEvent()

    data object CloseApp : ScanEvent()

    data object OpenAppSettings : ScanEvent()

    data object ShowPermissionGrantedSnackBar : ScanEvent()

}