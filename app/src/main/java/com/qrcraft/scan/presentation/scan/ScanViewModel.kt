package com.qrcraft.scan.presentation.scan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcraft.scan.presentation.scan.ScanAction.*
import com.qrcraft.scan.presentation.scan.ScanEvent.CloseApp
import com.qrcraft.scan.presentation.scan.ScanEvent.OpenAppSettings
import com.qrcraft.scan.presentation.scan.ScanEvent.RequestPermissionToSystem
import kotlinx.coroutines.channels.Channel
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
                    showPermissionDialog = !action.granted
                )
            }

            CustomDialogClosed -> {
                viewModelScope.launch {
                    eventChannel.send(CloseApp)
                }
            }

            RequestPermission -> {
                state = state.copy(
                    showPermissionDialog = false
                )
                viewModelScope.launch {
                    eventChannel.send(RequestPermissionToSystem)
                }
            }

            is UpdateAfterPermissionRequested -> {
                state = state.copy(
                    permissionGranted = action.isGranted,
                )

                if (action.isGranted) return

                viewModelScope.launch {
                    eventChannel.send(if (action.canRequestAgain) CloseApp else OpenAppSettings)
                }
            }
        }
    }

}