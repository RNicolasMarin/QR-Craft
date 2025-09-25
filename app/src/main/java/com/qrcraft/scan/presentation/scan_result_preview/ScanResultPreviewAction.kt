package com.qrcraft.scan.presentation.scan_result_preview

import android.graphics.Bitmap

sealed interface ScanResultPreviewAction {

    data class SetNonSavedQrContent(
        val qrCodeId: Int
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

    data object CheckUncheckFavourite: ScanResultPreviewAction

    data class SaveQrImage(
        val bitmap: Bitmap
    ): ScanResultPreviewAction

    data object ClearMessageSnackBar: ScanResultPreviewAction

}