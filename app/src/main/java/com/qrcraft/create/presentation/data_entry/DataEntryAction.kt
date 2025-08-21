package com.qrcraft.create.presentation.data_entry

import com.qrcraft.create.presentation.create_qr.QrTypeUI

sealed interface DataEntryAction {

    data object GoBackToCreateQr: DataEntryAction

    data class SetQrType(
        val qrTypeUI: QrTypeUI
    ): DataEntryAction

    sealed class UpdateQrContent(open val content: String): DataEntryAction {
        data class UpdateText(override val content: String): UpdateQrContent(content)
        data class UpdateLink(override val content: String): UpdateQrContent(content)
        data class UpdateContactName(override val content: String): UpdateQrContent(content)
        data class UpdateContactEmail(override val content: String): UpdateQrContent(content)
        data class UpdateContactPhone(override val content: String): UpdateQrContent(content)
        data class UpdatePhoneNumber(override val content: String): UpdateQrContent(content)
        data class UpdateGeolocationLatitude(override val content: String): UpdateQrContent(content)
        data class UpdateGeolocationLongitude(override val content: String): UpdateQrContent(content)
        data class UpdateWifiSsid(override val content: String): UpdateQrContent(content)
        data class UpdateWifiPassword(override val content: String): UpdateQrContent(content)
        data class UpdateWifiEncryption(override val content: String): UpdateQrContent(content)
    }

    data object GoToPreview: DataEntryAction

}