package com.qrcraft.create.domain

import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.*

class RawContentGenerator {

    fun createContent(qrType: QrType): String {
        return when (qrType) {
            is Link -> qrType.rawContent
            is Contact -> "BEGIN:VCARD\nVERSION:3.0\nN:${qrType.name}\nTEL:${qrType.phone}\nEMAIL:${qrType.email}\nEND:VCARD"
            is PhoneNumber -> "tel:${qrType.rawContent}"
            is Geolocation -> "geo:${qrType.latitude},${qrType.longitude}"
            is Wifi -> "WIFI:T:${qrType.encryption};S:${qrType.ssid};P:${qrType.password};;"
            is Text -> qrType.rawContent
        }
    }
}