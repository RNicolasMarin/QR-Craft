package com.qrcraft.scan.domain

import com.qrcraft.scan.domain.QrType.*

class QrTypeDetector {

    fun getQrType(rawContent: String): QrType {
        return when {
            rawContent.isLink() -> Link(rawContent)
            rawContent.isContact() -> Contact(rawContent)
            rawContent.isPhoneNumber() -> PhoneNumber(rawContent)
            rawContent.isGeolocation() -> Geolocation(rawContent)
            rawContent.isWifi() -> Wifi(rawContent)
            else -> Text(rawContent)
        }
    }
}

fun String.isLink(): Boolean {
    return startsWith("http://") || startsWith("https://")
}

fun String.isContact(): Boolean {
    return startsWith("BEGIN:VCARD")
}

fun String.isPhoneNumber(): Boolean {
    return startsWith("+") || isAllPhoneNumberValidCharacters()
}

fun String.isAllPhoneNumberValidCharacters(): Boolean {
    return all { it ->
        val isSpace = it == ' '
        val isParenthesis = it == '(' || it == ')'
        val isDash = it == '—' || it == '–' || it == '-'
        it.isDigit() || isSpace || isParenthesis || isDash
    }
}

fun String.isGeolocation(): Boolean {
    val values = split(", ")
    if (values.size != 2) return false

    val latitude = values[0].toDoubleOrNull()
    val longitude = values[1].toDoubleOrNull()

    if (latitude == null || longitude == null) return false

    return latitude in -90.0.. 90.0 && longitude in -180.00..180.00
}

fun String.isWifi(): Boolean {
    return startsWith("WIFI:")
}