package com.qrcraft.core.domain

import com.qrcraft.core.domain.QrCodeTypeId.CONTACT_ID
import com.qrcraft.core.domain.QrCodeTypeId.GEOLOCATION_ID
import com.qrcraft.core.domain.QrCodeTypeId.LINK_ID
import com.qrcraft.core.domain.QrCodeTypeId.PHONE_NUMBER_ID
import com.qrcraft.core.domain.QrCodeTypeId.WIFI_ID
import com.qrcraft.scan.domain.QrCodeType
import com.qrcraft.scan.domain.QrCodeType.*
import com.qrcraft.scan.domain.tryToGetContact
import com.qrcraft.scan.domain.tryToGetGeolocation
import com.qrcraft.scan.domain.tryToGetPhoneNumber
import com.qrcraft.scan.domain.tryToGetWifi

class QrCodeTypeConverter {

    fun convertToType(typeCode: Int, rawContent: String): QrCodeType {
        val type = when (typeCode) {
            LINK_ID -> Link
            CONTACT_ID -> rawContent.tryToGetContact()
            PHONE_NUMBER_ID -> rawContent.tryToGetPhoneNumber()
            GEOLOCATION_ID -> rawContent.tryToGetGeolocation()
            WIFI_ID -> rawContent.tryToGetWifi()
            else -> Text
        }
        return type ?: Text
    }
}