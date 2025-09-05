package com.qrcraft.app

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Scan: Screen()

    @Serializable
    data class ScanResult(
        val qrCodeId: Int
    ): Screen()

    @Serializable
    data object CreateQR: Screen()

    @Serializable
    data class DataEntry(
        val qrTypeOrdinal: Int
    ): Screen()

    @Serializable
    data class Preview(
        val qrCodeId: Int
    ): Screen()

    @Serializable
    data object ScanHistory: Screen()

}