package com.qrcraft.scan.domain

import com.qrcraft.scan.domain.QrType.*

class QrTypeDetector {

    fun getQrType(rawContent: String): QrType {
        return when {
            rawContent.isLink() -> Link(rawContent)
            rawContent.isContact() -> {
                val lines = rawContent.lines()
                val name = lines.getContentInLine("N:")?: run { lines.getContentInLine("FN:") }
                val email = lines.getContentInLine("EMAIL:")
                val phone = lines.getContentInLine("TEL:")
                Contact(
                    rawContent = rawContent,
                    name = name,
                    email = email,
                    phone = phone
                )
            }
            rawContent.isPhoneNumber() -> {
                PhoneNumber(rawContent.substringAfter("tel:"))
            }
            rawContent.isGeolocation() -> {
                Geolocation(rawContent.substringAfter("geo:"))
            }
            rawContent.isWifi() -> {

                val content = rawContent.removePrefix("WIFI:")

                // Split by ';' and then by ':'
                val map = content.split(';')
                    .mapNotNull {
                        val parts = it.split(':', limit = 2)
                        if (parts.size == 2) parts[0] to parts[1] else null
                    }
                    .toMap()

                val encryption = map["T"] // WPA, WPA2, WEP, nopass, etc.
                val ssid = map["S"]       // SSID value
                val password = map["P"]   // Password value

                Wifi(
                    rawContent = rawContent,
                    ssid = ssid,
                    password = password,
                    encryption = encryption
                )
            }
            else -> Text(rawContent)
        }
    }
}

fun List<String>.getContentInLine(prefix: String): String? {
    return firstOrNull { it.startsWith(prefix) }?.substringAfter(prefix)
}

fun String.isLink(): Boolean {
    return startsWith("http://") || startsWith("https://")
}

fun String.isContact(): Boolean {
    return startsWith("BEGIN:VCARD")
}

fun String.isPhoneNumber(): Boolean {
    return startsWith("tel:") || startsWith("+") || isAllPhoneNumberValidCharacters()
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
    val coordinates = if (startsWith("geo:")) substringAfter("geo:") else this

    val values = coordinates.split(",")
    if (values.size != 2) return false

    val latitude = values[0].toDoubleOrNull()
    val longitude = values[1].toDoubleOrNull()

    if (latitude == null || longitude == null) return false

    return latitude in -90.0.. 90.0 && longitude in -180.00..180.00
}

fun String.isWifi(): Boolean {
    return startsWith("WIFI:")
}