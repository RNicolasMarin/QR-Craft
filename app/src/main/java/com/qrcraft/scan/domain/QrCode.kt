package com.qrcraft.scan.domain

import com.qrcraft.scan.domain.ScannedOrGenerated.*

data class QrCode(
    var id: Long? = null,
    var rawContent: String,
    var title: String = "",
    var type: QrCodeType,
    var createdAt: Long = -1,
    var scannedOrGenerated: ScannedOrGenerated = SCANNED
)

sealed class QrCodeType(val typeCode: Int) {

    data object Link: QrCodeType(1)

    data class Contact(
        val name: String? = null,
        val email: String? = null,
        val phone: String? = null
    ): QrCodeType(2)

    data object PhoneNumber: QrCodeType(3)

    data class Geolocation(
        val latitude: String? = null,
        val longitude: String? = null
    ): QrCodeType(4)

    data class Wifi(
        val ssid: String? = null,
        val password: String? = null,
        val encryption: String? = null
    ): QrCodeType(5)

    data object Text: QrCodeType(6)
}

enum class ScannedOrGenerated {
    SCANNED,
    GENERATED
}
