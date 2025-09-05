package com.qrcraft.history.presentation.scan_history

sealed interface ScanHistoryAction {

    data object ChangeScannedOrGeneratedCode: ScanHistoryAction

    data class GoToPreview(
        val qrCodeId: Int
    ): ScanHistoryAction

    data class OpenMoreOptions(
        val qrCodeId: Int
    ): ScanHistoryAction

}