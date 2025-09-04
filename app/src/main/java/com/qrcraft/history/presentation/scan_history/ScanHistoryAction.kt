package com.qrcraft.history.presentation.scan_history

sealed interface ScanHistoryAction {

    data object ChangeScannedOrGeneratedCode: ScanHistoryAction
}