package com.qrcraft.create.domain

import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrCodeType.*
import com.qrcraft.scan.domain.isEmail
import com.qrcraft.scan.domain.isLatitude
import com.qrcraft.scan.domain.isLink
import com.qrcraft.scan.domain.isLongitude
import com.qrcraft.scan.domain.isPhoneNumber

class DataEntryValidator {

    fun isValidContent(qrType: QrCode): Boolean {
        val type = qrType.type
        return when (type) {
            is Link -> {
                qrType.rawContent.isLink()
            }
            is Contact -> {
                val validName = !type.name.isNullOrBlank()
                val validEmail = !type.email.isNullOrBlank() && type.email.isEmail()
                val validPhone = !type.phone.isNullOrBlank() && type.phone.isPhoneNumber()
                return validName && validEmail && validPhone
            }
            is PhoneNumber -> {
                qrType.rawContent.isPhoneNumber()
            }
            is Geolocation -> {
                val validLat = type.latitude?.isLatitude() ?: false
                val validLon = type.longitude?.isLongitude() ?: false
                return validLat && validLon
            }

            is Wifi -> {
                val validSsid = !type.ssid.isNullOrBlank()
                val validPassword = !type.password.isNullOrBlank()
                val validEncryption = !type.encryption.isNullOrBlank()
                return validSsid && validPassword && validEncryption
            }
            is Text -> {
                return qrType.rawContent.isNotBlank()
            }
        }
    }
}