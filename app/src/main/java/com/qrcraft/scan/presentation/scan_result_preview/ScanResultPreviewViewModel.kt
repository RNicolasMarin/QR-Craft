package com.qrcraft.scan.presentation.scan_result_preview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qrcraft.scan.domain.QrTypeDetector
import com.qrcraft.scan.presentation.scan_result_preview.ScanResultPreviewAction.*

class ScanResultPreviewViewModel(
    private val qrTypeDetector: QrTypeDetector
) : ViewModel() {

    var state by mutableStateOf(ScanResultPreviewState())
        private set

    fun onAction(action: ScanResultPreviewAction) {
        when (action) {
            is SetQrContent -> {
                val qrType = qrTypeDetector.getQrType(action.qrContent)
                state = state.copy(
                    qrType = qrType
                )
            }

            else -> Unit
        }
    }
}