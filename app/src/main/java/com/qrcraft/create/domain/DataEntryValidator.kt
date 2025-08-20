package com.qrcraft.create.domain

import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.*
import com.qrcraft.scan.domain.isEmail
import com.qrcraft.scan.domain.isLatitude
import com.qrcraft.scan.domain.isLink
import com.qrcraft.scan.domain.isLongitude
import com.qrcraft.scan.domain.isPhoneNumber

class DataEntryValidator {

    fun isValidContent(qrType: QrType): Boolean {
        return when (qrType) {
            is Link -> {
                qrType.rawContent.isLink()
            }
            is Contact -> {
                val validName = !qrType.name.isNullOrBlank()
                val validEmail = !qrType.email.isNullOrBlank() && qrType.email.isEmail()
                val validPhone = !qrType.phone.isNullOrBlank() && qrType.phone.isPhoneNumber()
                return validName && validEmail && validPhone
            }
            is PhoneNumber -> {
                qrType.rawContent.isPhoneNumber()
            }
            is Geolocation -> {
                val validLat = qrType.latitude?.isLatitude() ?: false
                val validLon = qrType.longitude?.isLongitude() ?: false
                return validLat && validLon
            }

            is Wifi -> {
                val validSsid = !qrType.ssid.isNullOrBlank()
                val validPassword = !qrType.password.isNullOrBlank()
                val validEncryption = !qrType.encryption.isNullOrBlank()
                return validSsid && validPassword && validEncryption
            }
            is Text -> {
                return qrType.rawContent.isNotBlank()
            }
        }
    }
}