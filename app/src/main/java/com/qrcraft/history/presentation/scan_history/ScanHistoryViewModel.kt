package com.qrcraft.history.presentation.scan_history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcraft.core.domain.QrCodeRepository
import com.qrcraft.history.presentation.scan_history.ScanHistoryAction.*
import com.qrcraft.scan.domain.ScannedOrGenerated.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScanHistoryViewModel(
    private val repository: QrCodeRepository
): ViewModel() {

    var state by mutableStateOf(ScanHistoryState())
        private set

    init {
        viewModelScope.launch {
            snapshotFlow { state.scannedOrGenerated }.collectLatest {
                repository.getQrCodesScanned(
                    state.scannedOrGenerated
                ).collectLatest {
                    state = state.copy(
                        qrCodesScanned = it
                    )
                }
            }
        }
        viewModelScope.launch {
            snapshotFlow { state.scannedOrGenerated }.collectLatest {
                repository.getQrCodesGenerated(
                    state.scannedOrGenerated
                ).collectLatest {
                    state = state.copy(
                        qrCodesGenerated = it
                    )
                }
            }
        }
    }

    fun onAction(action: ScanHistoryAction) {
        when (action) {
            ChangeScannedOrGeneratedCode -> {
                state = state.copy(
                    scannedOrGenerated = when(state.scannedOrGenerated) {
                        SCANNED -> GENERATED
                        GENERATED -> SCANNED
                    }
                )
            }
            is OpenMoreOptions -> {
                state = state.copy(
                    selectedQrCode = action.qrCode
                )
            }
            is CloseMoreOptions -> {
                state = state.copy(
                    selectedQrCode = null
                )
            }
            is DeleteQrCode -> {
                viewModelScope.launch {
                    state.selectedQrCode?.let {
                        repository.delete(it)
                    }
                    state = state.copy(
                        selectedQrCode = null
                    )
                }
            }
            is CheckUncheckFavourite -> {
                viewModelScope.launch {
                    val qrCode = action.qrCode

                    repository.upsert(
                        qrCode.copy(
                            isFavourite = !qrCode.isFavourite
                        )
                    )
                }
            }
            is GoToPreview, is ShareContent, OnCreateQr, OnScanQr -> Unit
        }
    }
}