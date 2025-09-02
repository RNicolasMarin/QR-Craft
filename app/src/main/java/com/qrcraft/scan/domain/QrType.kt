package com.qrcraft.scan.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class QrType(
    open val rawContent: String,
    open var title: String = ""
) {

    data class Link(
        override val rawContent: String,
        override var title: String = ""
    ): QrType(rawContent, title)

    data class Contact(
        override val rawContent: String,
        override var title: String = "",
        val name: String? = null,
        val email: String? = null,
        val phone: String? = null
    ): QrType(rawContent, title)

    data class PhoneNumber(
        override val rawContent: String,
        override var title: String = ""
    ): QrType(rawContent, title)

    data class Geolocation(
        override val rawContent: String,
        override var title: String = "",
        val latitude: String? = null,
        val longitude: String? = null
    ): QrType(rawContent, title)

    data class Wifi(
        override val rawContent: String,
        override var title: String = "",
        val ssid: String? = null,
        val password: String? = null,
        val encryption: String? = null
    ): QrType(rawContent, title)

    data class Text(
        override val rawContent: String,
        override var title: String = ""
    ): QrType(rawContent, title)
}