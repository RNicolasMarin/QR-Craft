package com.qrcraft.history.presentation.scan_history

import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.ScannedOrGenerated
import com.qrcraft.scan.domain.ScannedOrGenerated.*

data class ScanHistoryState(
    var scannedOrGenerated: ScannedOrGenerated = SCANNED,
    var qrCodes: List<QrCode> = emptyList()
)
