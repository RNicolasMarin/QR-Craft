package com.qrcraft.scan.domain

import com.qrcraft.core.domain.QrCodeTypeId.CONTACT_ID
import com.qrcraft.core.domain.QrCodeTypeId.GEOLOCATION_ID
import com.qrcraft.core.domain.QrCodeTypeId.LINK_ID
import com.qrcraft.core.domain.QrCodeTypeId.PHONE_NUMBER_ID
import com.qrcraft.core.domain.QrCodeTypeId.TEXT_ID
import com.qrcraft.core.domain.QrCodeTypeId.WIFI_ID
import com.qrcraft.scan.domain.ScannedOrGenerated.*

data class QrCode(
    var id: Int = -1,
    var rawContent: String,
    var title: String = "",
    var type: QrCodeType,
    var createdAt: Long = -1,
    var scannedOrGenerated: ScannedOrGenerated = SCANNED
)

sealed class QrCodeType(val typeId: Int) {

    data object Link: QrCodeType(LINK_ID)

    data class Contact(
        val name: String? = null,
        val email: String? = null,
        val phone: String? = null
    ): QrCodeType(CONTACT_ID)

    data class PhoneNumber(
        val phone: String? = null,
    ): QrCodeType(PHONE_NUMBER_ID)

    data class Geolocation(
        val latitude: String? = null,
        val longitude: String? = null
    ): QrCodeType(GEOLOCATION_ID)

    data class Wifi(
        val ssid: String? = null,
        val password: String? = null,
        val encryption: String? = null
    ): QrCodeType(WIFI_ID)

    data object Text: QrCodeType(TEXT_ID)
}

enum class ScannedOrGenerated(val typeValue: Int) {
    SCANNED(0),
    GENERATED(1)
}
