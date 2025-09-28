package com.qrcraft.scan.presentation.scan_result_preview

import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrTypeTextStateDomain
import com.qrcraft.scan.domain.QrTypeTextStateDomain.*

data class ScanResultPreviewState(
    val qrCode: QrCode? = null,
    var textState: QrTypeTextStateDomain = TEXT_SHORT
)
