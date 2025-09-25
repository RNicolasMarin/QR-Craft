package com.qrcraft.scan.presentation.scan

sealed interface ScanAction {

    data class UpdateGrantedInitially(val granted: Boolean): ScanAction

    data object CustomDialogClosed: ScanAction

    data object RequestPermission: ScanAction

    data class UpdateAfterPermissionRequested(
        val isGranted: Boolean,
        val canRequestAgain: Boolean
    ): ScanAction

    data object ScannerLoading: ScanAction

    data object ScannerQrNotFound: ScanAction

    data object ScannerRestartRunning: ScanAction

    data class ScannerSuccess(
        val qrContent: String
    ): ScanAction

    data object OnCreateQr: ScanAction
    data object OnScanHistory: ScanAction

    data class TryToggleFlashlight(
        val isCheckingAvailable: Boolean
    ): ScanAction

    data class ToggleFlashlight(
        val flashOn: Boolean? = null
    ): ScanAction

    data object PickImage: ScanAction

    data object ClearMessageSnackBar: ScanAction

}