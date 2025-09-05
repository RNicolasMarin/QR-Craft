package com.qrcraft.scan.presentation.scan_result_preview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcraft.core.domain.QrCodeRepository
import com.qrcraft.scan.presentation.scan_result_preview.ScanResultPreviewAction.*
import kotlinx.coroutines.launch

class ScanResultPreviewViewModel(
    private val repository: QrCodeRepository
) : ViewModel() {

    var state by mutableStateOf(ScanResultPreviewState())
        private set

    fun onAction(action: ScanResultPreviewAction) {
        when (action) {
            is SetNonSavedQrContent -> {
                viewModelScope.launch {
                    val result = repository.getQrCode(action.qrCodeId)

                    result?.let {
                        state = state.copy(
                            qrType = result
                        )
                    }
                }
            }

            is UpdateTitle -> {

                val newTitle = when {
                    action.title.length > 32 -> action.title.substring(0, 32)
                    else -> action.title
                }

                if (state.qrType?.title == newTitle) return

                state = state.copy(
                    qrType = state.qrType?.copy(
                        title = newTitle
                    )
                )
            }

            OnScreenRemoved -> {
                viewModelScope.launch {
                    state.qrType?.let {
                        repository.upsert(it)
                    }
                }
            }

            else -> Unit
        }
    }
}