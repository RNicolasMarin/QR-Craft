package com.qrcraft.scan.presentation.scan_result_preview

sealed interface ScanResultPreviewAction {

    data class SetQrContent(val qrContent: String): ScanResultPreviewAction

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
}