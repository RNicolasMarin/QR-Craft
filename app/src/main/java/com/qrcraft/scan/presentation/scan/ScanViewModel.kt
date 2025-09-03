package com.qrcraft.scan.presentation.scan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcraft.scan.presentation.scan.ScanAction.*
import com.qrcraft.scan.presentation.scan.ScanEvent.CloseApp
import com.qrcraft.scan.presentation.scan.ScanEvent.GoToScanResult
import com.qrcraft.scan.presentation.scan.ScanEvent.OpenAppSettings
import com.qrcraft.scan.presentation.scan.ScanEvent.RequestPermissionToSystem
import com.qrcraft.scan.presentation.scan.ScanEvent.ShowPermissionGrantedSnackBar
import com.qrcraft.scan.presentation.scan.ScanInfoToShow.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScanViewModel: ViewModel() {

    var state by mutableStateOf(ScanState())
        private set

    private val eventChannel = Channel<ScanEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ScanAction) {
        when (action) {
            is UpdateGrantedInitially -> {
                state = state.copy(
                    permissionGranted = action.granted,
                    isScanning = action.granted,
                    infoToShow = if (action.granted) NONE else REQUEST_PERMISSION
                )
            }

            CustomDialogClosed -> {
                viewModelScope.launch {
                    eventChannel.send(CloseApp)
                }
            }

            RequestPermission -> {
                state = state.copy(
                    infoToShow = NONE
                )
                viewModelScope.launch {
                    eventChannel.send(RequestPermissionToSystem)
                }
            }

            is UpdateAfterPermissionRequested -> {
                state = state.copy(
                    permissionGranted = action.isGranted,
                    isScanning = action.isGranted
                )

                val event = when {
                    action.isGranted -> ShowPermissionGrantedSnackBar
                    action.canRequestAgain -> CloseApp
                    else -> OpenAppSettings
                }
                viewModelScope.launch {
                    eventChannel.send(event)
                }
            }

            ScannerLoading -> {
                if (!state.isScanning) return

                state = state.copy(
                    infoToShow = LOADING
                )
            }
            ScannerQrNotFound -> {
                state = state.copy(
                    isScanning = false,
                    infoToShow = NO_QR_FOUND
                )
            }
            is ScannerSuccess -> {
                if (!state.isScanning) return

                state = state.copy(
                    isScanning = false,
                )
                viewModelScope.launch {
                    delay(1000)
                    state = state.copy(
                        infoToShow = NONE
                    )
                    eventChannel.send(GoToScanResult(action.qrContent))
                }
            }

            ScannerRestartRunning -> {
                state = state.copy(
                    isScanning = true,
                    infoToShow = NONE
                )
            }

            OnCreateQr, OnScanHistory -> Unit
        }
    }

}