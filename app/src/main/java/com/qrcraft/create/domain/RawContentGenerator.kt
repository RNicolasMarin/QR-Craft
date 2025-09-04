package com.qrcraft.create.domain

import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrCodeType.*

class RawContentGenerator {

    fun createContent(qrType: QrCode): String {
        val type = qrType.type
        return when (type) {
            is Link -> qrType.rawContent
            is Contact -> "BEGIN:VCARD\nVERSION:3.0\nN:${type.name}\nTEL:${type.phone}\nEMAIL:${type.email}\nEND:VCARD"
            is PhoneNumber -> "tel:${type.phone}"
            is Geolocation -> "geo:${type.latitude},${type.longitude}"
            is Wifi -> "WIFI:T:${type.encryption};S:${type.ssid};P:${type.password};;"
            is Text -> qrType.rawContent
        }
    }
}