package com.qrcraft.scan.presentation.scan_result_preview

import com.qrcraft.scan.domain.ScannedOrGenerated

sealed interface ScanResultPreviewAction {

    data class SetNonSavedQrContent(
        val qrContent: String,
        val scannedOrGenerated: ScannedOrGenerated,
    ): ScanResultPreviewAction

    data object GoBack: ScanResultPreviewAction

    data class ShareContent(
        val qrContent: String
    ): ScanResultPreviewAction

    data class CopyContent(
        val qrContent: String
    ): ScanResultPreviewAction

    data class OpenLink(
        val link: String
    ): ScanResultPreviewAction

    data class UpdateTitle(
        val title: String
    ): ScanResultPreviewAction

    data object OnScreenRemoved : ScanResultPreviewAction

}