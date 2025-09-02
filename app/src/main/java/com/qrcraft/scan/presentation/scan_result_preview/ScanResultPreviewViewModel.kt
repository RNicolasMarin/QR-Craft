package com.qrcraft.scan.presentation.scan_result_preview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qrcraft.scan.domain.QrType.*
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

            is UpdateTitle -> {

                val newTitle = when {
                    action.title.length > 32 -> action.title.substring(0, 32)
                    else -> action.title
                }

                if (state.qrType?.title == newTitle) return

                state = state.copy(
                    qrType = with(state.qrType) {
                        when (this) {
                            is Contact -> copy(title = newTitle)
                            is Geolocation -> copy(title = newTitle)
                            is Link -> copy(title = newTitle)
                            is PhoneNumber -> copy(title = newTitle)
                            is Text -> copy(title = newTitle)
                            is Wifi -> copy(title = newTitle)
                            null -> null
                        }
                    }
                )
            }

            else -> Unit
        }
    }
}