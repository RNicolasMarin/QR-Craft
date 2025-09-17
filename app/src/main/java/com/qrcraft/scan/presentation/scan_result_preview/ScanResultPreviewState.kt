package com.qrcraft.scan.presentation.scan_result_preview

import com.qrcraft.scan.domain.QrCode

data class ScanResultPreviewState(
    val qrCode: QrCode? = null
)
