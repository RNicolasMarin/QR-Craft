package com.qrcraft.scan.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class QrType(open val rawContent: String) {

    data class Link(
        override val rawContent: String
    ): QrType(rawContent)

    data class Contact(
        override val rawContent: String,
        val name: String?,
        val email: String?,
        val phone: String?
    ): QrType(rawContent)

    data class PhoneNumber(
        override val rawContent: String
    ): QrType(rawContent)

    data class Geolocation(
        override val rawContent: String
    ): QrType(rawContent)

    data class Wifi(
        override val rawContent: String,
        val ssid: String?,
        val password: String?,
        val encryption: String?,
    ): QrType(rawContent)

    data class Text(
        override val rawContent: String
    ): QrType(rawContent)
}