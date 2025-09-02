package com.qrcraft.create.presentation.data_entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrcraft.create.domain.DataEntryValidator
import com.qrcraft.create.domain.RawContentGenerator
import com.qrcraft.create.presentation.create_qr.QrTypeUI.*
import com.qrcraft.create.presentation.data_entry.DataEntryAction.*
import com.qrcraft.create.presentation.data_entry.DataEntryAction.UpdateQrContent.*
import com.qrcraft.create.presentation.data_entry.DataEntryEvent.GoToPreview
import com.qrcraft.scan.domain.QrType.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DataEntryViewModel(
    val dataEntryValidator: DataEntryValidator,
    val rawContentGenerator: RawContentGenerator
): ViewModel() {

    var state by mutableStateOf(DataEntryState())
        private set

    private val eventChannel = Channel<DataEntryEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: DataEntryAction) {
        when (action) {
            is SetQrType -> {
                if (state.qrType != null) return
                state = state.copy(
                    qrType = when (action.qrTypeUI) {
                        TEXT -> Text("")
                        LINK -> Link("")
                        PHONE_NUMBER -> PhoneNumber("")
                        CONTACT -> Contact("")
                        GEOLOCATION -> Geolocation("")
                        WIFI -> Wifi("")
                    }
                )
            }
            is UpdateQrContent -> {
                val type = when (action) {
                    is UpdateText -> {
                        Text(action.content)
                    }
                    is UpdateLink -> {
                        Link(action.content)
                    }
                    is UpdateContactName, is UpdateContactEmail, is UpdateContactPhone -> {
                        val current: Contact = (state.qrType as? Contact)?: return

                        val name = if (action is UpdateContactName) action.content else current.name
                        val email = if (action is UpdateContactEmail) action.content else current.email
                        val phone = if (action is UpdateContactPhone) action.content else current.phone

                        Contact(
                            rawContent = "",
                            name = name,
                            email = email,
                            phone = phone
                        )
                    }
                    is UpdatePhoneNumber -> {
                        PhoneNumber(action.content)
                    }
                    is UpdateGeolocationLatitude, is UpdateGeolocationLongitude -> {
                        val current: Geolocation = (state.qrType as? Geolocation)?: return

                        val latitude = if (action is UpdateGeolocationLatitude) action.content else current.latitude
                        val longitude = if (action is UpdateGeolocationLongitude) action.content else current.longitude

                        Geolocation(
                            rawContent = "",
                            latitude = latitude,
                            longitude = longitude
                        )
                    }
                    is UpdateWifiSsid, is UpdateWifiPassword, is UpdateWifiEncryption -> {
                        val current: Wifi = (state.qrType as? Wifi)?: return

                        val ssid = if (action is UpdateWifiSsid) action.content else current.ssid
                        val password = if (action is UpdateWifiPassword) action.content else current.password
                        val encryption = if (action is UpdateWifiEncryption) action.content else current.encryption

                        Wifi(
                            rawContent = "",
                            ssid = ssid,
                            password = password,
                            encryption = encryption
                        )
                    }
                }
                val canGenerate = dataEntryValidator.isValidContent(type)

                state = state.copy(
                    qrType = type,
                    canGenerate = canGenerate
                )
            }
            is GenerateRawContent -> {
                state.qrType?.let {
                    val content = rawContentGenerator.createContent(it)
                    viewModelScope.launch {
                        eventChannel.send(GoToPreview(content))
                    }
                }
            }
            else -> Unit
        }
    }
}