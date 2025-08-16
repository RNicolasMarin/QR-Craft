package com.qrcraft.scan.presentation.scan_result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qrcraft.scan.domain.QrTypeDetector
import com.qrcraft.scan.presentation.scan_result.ScanResultAction.*

class ScanResultViewModel(
    private val qrTypeDetector: QrTypeDetector
) : ViewModel() {

    var state by mutableStateOf(ScanResultState())
        private set

    fun onAction(action: ScanResultAction) {
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