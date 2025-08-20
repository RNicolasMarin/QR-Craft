package com.qrcraft.scan.domain

import com.qrcraft.scan.domain.QrType.*

class QrTypeDetector {

    fun getQrType(rawContent: String): QrType {
        val link = rawContent.tryToGetLink()
        if (link != null) return link

        val contact = rawContent.tryToGetContact()
        if (contact != null) return contact

        val phoneNumber = rawContent.tryToGetPhoneNumber()
        if (phoneNumber != null) return phoneNumber

        val geolocation = rawContent.tryToGetGeolocation()
        if (geolocation != null) return geolocation

        val wifi = rawContent.tryToGetWifi()
        if (wifi != null) return wifi

        return Text(rawContent)
    }
}

fun List<String>.getContentInLine(prefix: String): String? {
    return firstOrNull { it.startsWith(prefix) }?.substringAfter(prefix)
}

fun String.tryToGetLink(): Link? {
    val isValid = startsWith("http://") || startsWith("https://")
    return if (isValid) Link(this) else null
}

fun String.tryToGetContact(): Contact? {
    val isValid = startsWith("BEGIN:VCARD")
    if (!isValid) return null

    val lines = lines()
    val name = lines.getContentInLine("N:")?: run { lines.getContentInLine("FN:") }
    val email = lines.getContentInLine("EMAIL:")
    val phone = lines.getContentInLine("TEL:")

    return Contact(
        rawContent = this,
        name = name,
        email = email,
        phone = phone
    )
}

fun String.tryToGetPhoneNumber(): PhoneNumber? {
    val isValid = startsWith("tel:") || startsWith("+") || isAllPhoneNumberValidCharacters()
    if (!isValid) return null

    return PhoneNumber(substringAfter("tel:"))
}

fun String.isAllPhoneNumberValidCharacters(): Boolean {
    return all { it ->
        val isSpace = it == ' '
        val isParenthesis = it == '(' || it == ')'
        val isDash = it == '—' || it == '–' || it == '-'
        it.isDigit() || isSpace || isParenthesis || isDash
    }
}

fun String.tryToGetGeolocation(): Geolocation? {
    val coordinates = if (startsWith("geo:")) substringAfter("geo:") else this

    var values = coordinates.split(",")
    if (values.size == 1) {
        values = coordinates.split(", ")
    }
    if (values.size != 2) return null

    val latitude = values[0].toDoubleOrNull()
    val longitude = values[1].toDoubleOrNull()

    if (latitude == null || longitude == null) return null
    if (latitude !in -90.0.. 90.0 || longitude !in -180.00..180.00) return null


    return Geolocation(coordinates, latitude.toString(), longitude.toString())
}

fun String.tryToGetWifi(): Wifi? {
    val isValid = startsWith("WIFI:")
    if (!isValid) return null

    val content = removePrefix("WIFI:")

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

    return Wifi(
        rawContent = this,
        ssid = ssid,
        password = password,
        encryption = encryption
    )
}