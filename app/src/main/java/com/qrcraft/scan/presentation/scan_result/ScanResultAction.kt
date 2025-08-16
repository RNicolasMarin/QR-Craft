package com.qrcraft.scan.presentation.scan_result

sealed interface ScanResultAction {

    data class SetQrContent(val qrContent: String): ScanResultAction

    data object GoBackToScan: ScanResultAction

    data class ShareContent(
        val qrContent: String
    ): ScanResultAction

    data class CopyContent(
        val qrContent: String
    ): ScanResultAction

    data class OpenLink(
        val link: String
    ): ScanResultAction
}