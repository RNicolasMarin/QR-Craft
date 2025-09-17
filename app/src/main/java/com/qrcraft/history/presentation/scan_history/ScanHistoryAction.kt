package com.qrcraft.history.presentation.scan_history

import com.qrcraft.scan.domain.QrCode

sealed interface ScanHistoryAction {

    data object ChangeScannedOrGeneratedCode: ScanHistoryAction

    data class GoToPreview(
        val qrCodeId: Int
    ): ScanHistoryAction

    data class OpenMoreOptions(
        val qrCode: QrCode
    ): ScanHistoryAction

    data object CloseMoreOptions: ScanHistoryAction

    data class ShareContent(
        val qrContent: String
    ): ScanHistoryAction

    data object DeleteQrCode: ScanHistoryAction

    data class CheckUncheckFavourite(
        val qrCode: QrCode
    ): ScanHistoryAction

}