package com.qrcraft.scan.domain

sealed class QrType(open val rawContent: String) {

    data class Link(
        override val rawContent: String
    ): QrType(rawContent)

    data class Contact(
        override val rawContent: String
    ): QrType(rawContent)

    data class PhoneNumber(
        override val rawContent: String
    ): QrType(rawContent)

    data class Geolocation(
        override val rawContent: String
    ): QrType(rawContent)

    data class Wifi(
        override val rawContent: String
    ): QrType(rawContent)

    data class Text(
        override val rawContent: String
    ): QrType(rawContent)
}