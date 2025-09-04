package com.qrcraft.core.domain

import com.qrcraft.scan.domain.QrCodeType
import com.qrcraft.scan.domain.QrCodeType.*
import com.qrcraft.scan.domain.tryToGetContact
import com.qrcraft.scan.domain.tryToGetGeolocation
import com.qrcraft.scan.domain.tryToGetWifi

class QrCodeTypeConverter {

    fun convertToType(typeCode: Int, rawContent: String): QrCodeType {
        val type = when (typeCode) {
            1 -> Link
            2 -> rawContent.tryToGetContact()
            3 -> PhoneNumber
            4 -> rawContent.tryToGetGeolocation()
            5 -> rawContent.tryToGetWifi()
            else -> Text
        }
        return type ?: Text
    }
}