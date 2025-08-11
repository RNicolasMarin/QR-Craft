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

}